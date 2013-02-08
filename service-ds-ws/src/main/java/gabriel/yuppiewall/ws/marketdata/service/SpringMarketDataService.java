package gabriel.yuppiewall.ws.marketdata.service;

import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.service.MarketDataServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("MarketDataService")
public class SpringMarketDataService extends MarketDataServiceImpl {

	@Autowired
	@Qualifier("CacheEndOfDayDataRepository")
	private EndOfDayDataRepository eodRepository;

	@Override
	protected EndOfDayDataRepository getEndOfDayDataRepository() {
		return eodRepository;
	}

}
