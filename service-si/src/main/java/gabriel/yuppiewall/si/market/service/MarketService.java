package gabriel.yuppiewall.si.market.service;

import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.market.service.MarketServiceImpl;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service("MarketService")
public class MarketService extends MarketServiceImpl {

	@Override
	public Date getExchangeCurrentTime(Exchange exchange) {
		// TODO get exchange time zone and convert local time to that
		return new Date();

	}

	@Override
	protected MarketRepository getMarketRepository() {
		// TODO Auto-generated method stub
		return null;
	}

}
