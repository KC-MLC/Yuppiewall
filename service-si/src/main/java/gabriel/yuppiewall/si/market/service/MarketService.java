package gabriel.yuppiewall.si.market.service;

import java.util.Date;

import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.market.repository.MarketRepository;
import gabriel.yuppiewall.market.service.MarketServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("MarketService")
public class MarketService extends MarketServiceImpl {

	@Autowired
	@Qualifier("JPAMarketRepository")
	private MarketRepository marketRepository;

	@Override
	protected MarketRepository getMarketRepository() {
		return marketRepository;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void createIfNotPresent(Exchange_ exchange, Date date) {
		super.createIfNotPresent(exchange, date);
	}

}