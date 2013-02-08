package gabriel.yuppiewall.ws.scanner.service;

import gabriel.yuppiewall.common.util.Filter;
import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.ws.scanner.service.DataStore.STATUS;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("regionServerInitilizer")
public class RegionServerInitilizer {

	@Autowired
	@Qualifier("dataStore")
	private DataStore dataStore;

	@Autowired
	@Qualifier("YahooWEBEndOfDayDataRepository")
	private EndOfDayDataRepository eodRepository;

	@Autowired
	private EodScheduler eodScheduler;

	@Autowired
	private SystemDataRepository systemDataRepository;

	public void init() {
		// check if need is there to initilize

		if (dataStore.getStatus() == STATUS.NOT_INITIALIZED) {
			synchronized (dataStore) {
				if (dataStore.getStatus() == STATUS.NOT_INITIALIZED) {
					dataStore.setStatus(STATUS.IN_PROGRESS);
					dataStore.clear();
					try {

						// get List of stock I am managing
						List<Instrument> symbolList = getManagedInstrumentList();
						int i = 0;
						for (Instrument instrument : symbolList) {

							if (i++ > 10)
								break;
							System.out.println(instrument.getSymbol());
							Exchange exchange = instrument.getExchange();
							Exchange e1 = dataStore.getExchange(exchange);
							if (e1 == null) {
								exchange = systemDataRepository
										.getExchange(exchange);
								dataStore.setExchange(exchange);
								eodScheduler.createScheduler(exchange);
							}
							instrument.setExchange(exchange);
							List<EndOfDayData> eodList = eodRepository
									.findAllEndOfDayData(new Instrument(
											instrument.getSymbol()),
											new Filter<EndOfDayData>() {

												@Override
												public boolean filter(
														EndOfDayData eod) {
													Calendar cal = Calendar
															.getInstance();
													cal.setTime(eod.getDate());
													return (cal
															.get(Calendar.YEAR) > 2010);
												}
											});
							if (eodList == null) {
								dataStore.addStockWithError(instrument
										.getSymbol());
								continue;
							}
							instrument.setEodList(eodList);
							dataStore.setInstrument(instrument);
						}
						dataStore.setStatus(STATUS.INITIALIZED);
					} catch (Exception e) {
						dataStore.setStatus(STATUS.NOT_INITIALIZED);
					}
				}
			}
		}

	}

	private List<Instrument> getManagedInstrumentList() {
		// find my id
		/*
		 * String requestID = dataStore.generateRequestID(); // make server call
		 * to update my id RegionServerService regionServerService;
		 * 
		 * return
		 * regionServerService.getManagedInstrument(dataStore.getStoreID());
		 */
		System.out.println("STARTING GETTING OF SERVER");
		/*
		 * Server server = new
		 * Server("http://localhost:7081/service-ds-ws/api");
		 * dataStore.setServer(server);
		 */
		Server server = dataStore.getServer();
		dataStore.setServer(server);
		System.out.println("GOT SERVER" + server);

		return systemDataRepository.getManagedInstrument(server);
	}
}
