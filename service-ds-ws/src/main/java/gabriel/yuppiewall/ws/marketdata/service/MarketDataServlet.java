package gabriel.yuppiewall.ws.marketdata.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import gabriel.util.rpc.http.RPCServlet;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.domain.InstrumentMarketDetail;
import gabriel.yuppiewall.marketdata.service.MarketDataService;

//@Controller
//@RequestMapping("/cache")
@SuppressWarnings("serial")
// @WebServlet("/MarketDataService")
public class MarketDataServlet extends RPCServlet implements MarketDataService {

	@Autowired
	@Qualifier("MarketDataService")
	private MarketDataService marketDataService;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		WebApplicationContext appContext = WebApplicationContextUtils
				.getWebApplicationContext(servletConfig.getServletContext());
		marketDataService = (MarketDataService) appContext
				.getBean("MarketDataService");
	}

	@Override
	public Collection<InstrumentMarketDetail> getInstrmentCurrentStatus(
			List<Instrument> instrument) {
		return marketDataService.getInstrmentCurrentStatus(instrument);
	}

	@Override
	public Map<String, List<EndOfDayData>> findAllEndOfDayData(
			Collection<Instrument> instrument, int offset, int start) {
		return marketDataService.findAllEndOfDayData(instrument, offset, start);
	}

}
