package gabriel.yuppiewall.market.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.market.domain.TradeDay_;
import gabriel.yuppiewall.market.repository.MarketRepository;

import java.util.Date;

public abstract class MarketServiceImpl implements MarketService {

	@Override
	public void createIfNotPresent(Exchange_ exchange, Date date) {
		TradeDay_ tradeDay = getMarketRepository().getTradeDay(exchange, date);
		if (tradeDay != null)
			return;

		TradeDay_ td = getMarketRepository().getLastTradeDay(exchange);
		if (td.getDate() != null && td.getDate().after(date)) {
			// find this guy's position
			td = getMarketRepository().findTradeDayBefore(
					new TradeDay_(exchange, date, 0));
			// update all the record with plus 1
			getMarketRepository().incrementTradeDay(date, exchange);

		}
		td = new TradeDay_(exchange, date, td.getBusinessday() + 1);
		getMarketRepository().createTradeDay(td);
	}

	@Override
	public Date getExchangeCurrentTime(Exchange_ exchange) {
		// TODO get exchange time zone and make the date
		return new Date();
	}

	@Override
	public Exchange_ getExchange(Instrument instrument) {
		return getMarketRepository().getExchange(instrument);		
	}

	protected abstract MarketRepository getMarketRepository();

}
