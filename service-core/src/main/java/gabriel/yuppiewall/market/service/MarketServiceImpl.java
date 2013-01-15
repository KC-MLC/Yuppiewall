package gabriel.yuppiewall.market.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.market.domain.TradeDay;
import gabriel.yuppiewall.market.repository.MarketRepository;

import java.util.Date;

public abstract class MarketServiceImpl implements MarketService {

	@Override
	public void createIfNotPresent(Exchange exchange, Date date) {
		TradeDay tradeDay = getMarketRepository().getTradeDay(exchange, date);
		if (tradeDay != null)
			return;

		TradeDay td = getMarketRepository().getLastTradeDay(exchange);
		if (td.getDate() != null && td.getDate().after(date)) {
			// find this guy's position
			td = getMarketRepository().findTradeDayBefore(
					new TradeDay(exchange, date, 0));
			// update all the record with plus 1
			getMarketRepository().incrementTradeDay(date, exchange);

		}
		td = new TradeDay(exchange, date, td.getBusinessday() + 1);
		getMarketRepository().createTradeDay(td);
	}

	@Override
	public Date getExchangeCurrentTime(Exchange exchange) {
		// TODO get exchange time zone and make the date
		return new Date();
	}

	@Override
	public Exchange getExchange(Instrument instrument) {
		return getMarketRepository().getExchange(instrument);		
	}

	protected abstract MarketRepository getMarketRepository();

}
