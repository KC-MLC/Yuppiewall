package gabriel.yuppiewall.spring.marketdata.service;

import gabriel.yuppiewall.market.service.MarketService;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.service.EndOfDayServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "endOfDayService")
public class SpringEndOfDayService extends EndOfDayServiceImpl {

	@Autowired
	@Qualifier("JPAEndOfDayDataRepository")
	private EndOfDayDataRepository endOfDayDataRepository;

	@Autowired
	private MarketService marketService;

	@Override
	protected EndOfDayDataRepository getEndOfDayDataRepository() {
		return endOfDayDataRepository;
	}

	@Override
	protected MarketService getMarketService() {
		return marketService;
	}
}
