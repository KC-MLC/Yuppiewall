package gabriel.yuppiewall.spring.marketdata.service;

import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.domain.InstrumentMarketDetail;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.marketdata.service.MarketDataService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("MarketDataService")
public class DistributedMarketDataService implements MarketDataService {

	@Autowired
	@Qualifier("SystemDataRepository")
	private SystemDataRepository systemDataRepository;

	private Map<String, MarketDataService> marketDataServiceProviders;

	private void init() {
		marketDataServiceProviders = new HashMap<>();
		Collection<Server> serverList = systemDataRepository.getServerList();
		for (Server server : serverList) {
			try {
				marketDataServiceProviders.put(server.getServerContext(),
						new ProxyMarketDataService(server));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public Collection<InstrumentMarketDetail> getInstrmentCurrentStatus(
			List<Instrument> instruments) {
		if (marketDataServiceProviders == null) {
			init();
		}
		Iterator<Instrument> itr = instruments.iterator();
		Set<MarketDataService> providers = new HashSet<>();
		while (itr.hasNext()) {
			Instrument instrument = itr.next();
			instrument = systemDataRepository.getInstrument(instrument);

			providers
					.add(marketDataServiceProviders.get(instrument.getServer()));

		}

		Thread[] threads = new Thread[providers.size()];
		GetInstrmentCurrentStatus[] getInstrmentCurrentStatus = new GetInstrmentCurrentStatus[providers
				.size()];

		int j = 0;

		for (MarketDataService mds : providers) {

			(threads[j] = new Thread(
					getInstrmentCurrentStatus[j] = new GetInstrmentCurrentStatus(
							instruments, mds))).start();
			j += 1;
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
			}
		}

		List<InstrumentMarketDetail> retValue = new ArrayList<>();
		for (int i = 0; i < getInstrmentCurrentStatus.length; i++) {

			Collection<InstrumentMarketDetail> output = getInstrmentCurrentStatus[i]
					.getOutput();
			if (output == null)
				continue;
			retValue.addAll(output);
		}
		return retValue;
	}

	@Override
	public Map<String, List<EndOfDayData>> findAllEndOfDayData(
			Collection<Instrument> instruments, int offset, int start) {
		if (marketDataServiceProviders == null) {
			init();
		}
		Iterator<Instrument> itr = instruments.iterator();
		Set<MarketDataService> providers = new HashSet<>();
		while (itr.hasNext()) {
			Instrument instrument = itr.next();
			instrument = systemDataRepository.getInstrument(instrument);
			providers
					.add(marketDataServiceProviders.get(instrument.getServer()));

		}

		Thread[] threads = new Thread[providers.size()];
		FindAllEndOfDayData[] findAllEndOfDayData = new FindAllEndOfDayData[providers
				.size()];

		int j = 0;

		for (MarketDataService mds : providers) {

			(threads[j] = new Thread(
					findAllEndOfDayData[j] = new FindAllEndOfDayData(
							instruments, offset, start, mds))).start();
			j += 1;
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
			}
		}

		Map<String, List<EndOfDayData>> retValue = new HashMap<>();
		for (int i = 0; i < findAllEndOfDayData.length; i++) {

			Map<String, List<EndOfDayData>> output = findAllEndOfDayData[i]
					.getOutput();
			if (output == null)
				continue;
			Iterator<String> itrOutput = output.keySet().iterator();
			while (itrOutput.hasNext()) {
				String symbol = itrOutput.next();
				retValue.put(symbol, output.get(symbol));
			}

		}
		return retValue;
	}

	class GetInstrmentCurrentStatus implements Runnable {
		private MarketDataService mds;
		private List<Instrument> instruments;

		private Collection<InstrumentMarketDetail> output;

		public GetInstrmentCurrentStatus(List<Instrument> instruments,
				MarketDataService mds) {
			this.instruments = instruments;

			this.mds = mds;
		}

		public Collection<InstrumentMarketDetail> getOutput() {
			return output;
		}

		@Override
		public void run() {
			try {
				output = mds.getInstrmentCurrentStatus(instruments);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class FindAllEndOfDayData implements Runnable {
		private MarketDataService mds;
		private Collection<Instrument> instruments;
		private int offset;
		private int start;
		private Map<String, List<EndOfDayData>> output;

		public FindAllEndOfDayData(Collection<Instrument> instruments,
				int offset, int start, MarketDataService mds) {
			this.instruments = instruments;
			this.offset = offset;
			this.start = start;
			this.mds = mds;
		}

		public Map<String, List<EndOfDayData>> getOutput() {
			return output;
		}

		@Override
		public void run() {
			try {
				output = mds.findAllEndOfDayData(instruments, offset, start);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
