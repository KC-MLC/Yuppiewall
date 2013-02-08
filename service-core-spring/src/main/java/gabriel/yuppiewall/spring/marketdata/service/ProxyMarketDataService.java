package gabriel.yuppiewall.spring.marketdata.service;

import gabriel.util.rpc.http.HTTPProxy;
import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.domain.InstrumentMarketDetail;
import gabriel.yuppiewall.marketdata.service.MarketDataService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ProxyMarketDataService implements MarketDataService {

	private Server server;

	public ProxyMarketDataService(Server server) {
		this.server = server;
	}

	@Override
	public Collection<InstrumentMarketDetail> getInstrmentCurrentStatus(
			List<Instrument> instruments) {
		String url = server.getServerContext().replaceAll("/api", "");
		HTTPProxy proxy = new HTTPProxy(url);
		try {
			MarketDataService mds = (MarketDataService) proxy
					.getService(MarketDataService.class);
			return mds.getInstrmentCurrentStatus(instruments);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, List<EndOfDayData>> findAllEndOfDayData(
			Collection<Instrument> instrument, int offset, int start) {
		String url = server.getServerContext().replaceAll("/api", "");
		HTTPProxy proxy = new HTTPProxy(url);
		try {
			MarketDataService mds = (MarketDataService) proxy
					.getService(MarketDataService.class);
			return mds.findAllEndOfDayData(instrument, offset, start);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
