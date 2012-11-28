package gabriel.yuppiewall.jpa.market.repository;

import gabriel.yuppiewall.jpa.market.domain.JPAExchange;
import gabriel.yuppiewall.jpa.market.domain.JPATradeDay;
import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.market.domain.TradeDay_;
import gabriel.yuppiewall.market.repository.MarketRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository(value = "JPAMarketRepository")
public class JPAMarketRepository implements MarketRepository {

	@Autowired
	private TradeDayRepository tradeDayRepository;

	@Override
	public Date getTradingDate(Exchange_ exchange, Date toDate, int days) {

		throw new UnsupportedOperationException(
				"Not Implemented : getTradingDate");
	}

	@Override
	public TradeDay_ getTradeDay(Exchange_ exchange, Date date) {
		JPATradeDay td = tradeDayRepository.findOne(exchange.getName()
				+ new SimpleDateFormat("ddmmyyyy").format(date));
		return (td != null) ? td.getTradeDay() : null;
	}

	@Override
	public TradeDay_ getLastTradeDay(Exchange_ exchange) {

		JPATradeDay td = tradeDayRepository
				.findLastTradeDayByExchange(new JPAExchange(exchange));
		return (td != null) ? td.getTradeDay() : new TradeDay_(exchange, null,
				0);
	}

	@Override
	public void createTradeDay(TradeDay_ td) {
		tradeDayRepository.saveAndFlush(new JPATradeDay(td));

	}

}
