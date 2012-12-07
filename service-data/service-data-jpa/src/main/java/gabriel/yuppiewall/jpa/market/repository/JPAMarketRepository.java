package gabriel.yuppiewall.jpa.market.repository;

import gabriel.yuppiewall.jpa.market.domain.JPAExchange;
import gabriel.yuppiewall.jpa.market.domain.JPATradeDay;
import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.market.domain.TradeDay_;
import gabriel.yuppiewall.market.repository.MarketRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "JPAMarketRepository")
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
		JPATradeDay td = tradeDayRepository.findOne(exchange.getName() + ":"
				+ new SimpleDateFormat(TradeDay_.DATE_PATTERN).format(date));
		return (td != null) ? td.getTradeDay() : null;
	}

	@Override
	public TradeDay_ getLastTradeDay(Exchange_ exchange) {

		JPATradeDay td = tradeDayRepository
				.findLastTradeDayByExchange(new JPAExchange(exchange));
		return (td != null) ? new TradeDay_(exchange, td.getDate(),
				td.getBusinessday()) : new TradeDay_(exchange, null, 0);
	}

	@Override
	public void createTradeDay(TradeDay_ td) {
		tradeDayRepository.saveAndFlush(new JPATradeDay(td));

	}

	@Override
	public void incrementTradeDay(Date date, Exchange_ exchange) {
		tradeDayRepository.incrementBusinessdayBy1(date, new JPAExchange(
				exchange));

	}

	@Override
	public TradeDay_ findTradeDayBefore(TradeDay_ td_) {
		JPATradeDay td = tradeDayRepository.findLastTradeDayBeforeDate(
				td_.getDate(), new JPAExchange(td_.getExchange()));
		return (td == null/* Beginning of time */) ? new TradeDay_(
				td_.getExchange(), td_.getDate(), 0) : new TradeDay_(td_.getExchange(),
				td.getDate(), td.getBusinessday());
	}
}
