package gabriel.yuppiewall.market.service;

import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.market.domain.TradeDay_;
import gabriel.yuppiewall.market.repository.MarketRepository;

import java.util.Date;

public abstract class MarketServiceImpl implements MarketService {

	@Override
	public void createIfNotPresent(Exchange_ exchange, Date date) {
		TradeDay_ tradeDay = getMarketRepository().getTradeDay(exchange, date);
		if (tradeDay == null) {
			TradeDay_ td = getMarketRepository().getLastTradeDay(exchange);
			td = new TradeDay_(exchange, date, td.getBusinessday() + 1);
			getMarketRepository().createTradeDay(td);
		}

	}

	protected abstract MarketRepository getMarketRepository();

}
