package gabriel.yuppiewall.market.repository;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.market.domain.TradeDay;

import java.util.Date;

public interface MarketRepository {

	Date getTradingDate(Exchange exchange, Date toDate, int days);

	TradeDay getTradeDay(Exchange exchange, Date date);

	TradeDay getLastTradeDay(Exchange exchange);

	void createTradeDay(TradeDay td);

	void incrementTradeDay(Date date, Exchange exchange);

	TradeDay findTradeDayBefore(TradeDay td);

	Exchange getExchange(Instrument instrument);

}
