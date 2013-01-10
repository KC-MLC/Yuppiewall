package gabriel.yuppiewall.spring.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gabriel.yuppiewall.market.repository.MarketRepository;
import gabriel.yuppiewall.market.service.MarketServiceImpl;

@Service("marketService")
public class SpringMarketService extends MarketServiceImpl {

	@Autowired
	private MarketRepository marketRepository; 
	@Override
	protected MarketRepository getMarketRepository() {
		return marketRepository;
	}

}
