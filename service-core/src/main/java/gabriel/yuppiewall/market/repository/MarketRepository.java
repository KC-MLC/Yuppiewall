package gabriel.yuppiewall.market.repository;

import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.market.domain.TradeDay_;

import java.util.Date;

public interface MarketRepository {

	Date getTradingDate(Exchange_ exchange, Date toDate, int days);

	TradeDay_ getTradeDay(Exchange_ exchange, Date date);

	TradeDay_ getLastTradeDay(Exchange_ exchange);

	void createTradeDay(TradeDay_ td);

	void incrementTradeDay(Date date, Exchange_ exchange);

	TradeDay_ findTradeDayBefore(TradeDay_ td);

}
