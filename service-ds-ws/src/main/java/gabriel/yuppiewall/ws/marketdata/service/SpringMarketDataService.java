package gabriel.yuppiewall.ws.marketdata.service;

import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.service.MarketDataServiceImpl;

import org.springframework.stereotype.Service;

@Service("MarketDataService")
public class SpringMarketDataService extends MarketDataServiceImpl {

	@Override
	protected EndOfDayDataRepository getEndOfDayDataRepository() {
		// TODO Auto-generated method stub
		return null;
	}

}
