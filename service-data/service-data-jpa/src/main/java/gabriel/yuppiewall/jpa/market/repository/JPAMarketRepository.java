package gabriel.yuppiewall.jpa.market.repository;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.jpa.market.domain.JPAExchange;
import gabriel.yuppiewall.jpa.market.domain.JPAInstrument;
import gabriel.yuppiewall.jpa.market.domain.JPATradeDay;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.market.domain.TradeDay;
import gabriel.yuppiewall.market.repository.MarketRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "JPAMarketRepository")
public class JPAMarketRepository implements MarketRepository {

	@Autowired
	private TradeDayRepository tradeDayRepository;

	@Autowired
	private InstrumentRepository instrumentRepository;

	@Override
	public Date getTradingDate(Exchange exchange, Date toDate, int days) {

		throw new UnsupportedOperationException(
				"Not Implemented : getTradingDate");
	}

	@Override
	public TradeDay getTradeDay(Exchange exchange, Date date) {
		JPATradeDay td = tradeDayRepository.findOne(exchange.getName() + ":"
				+ new SimpleDateFormat(TradeDay.DATE_PATTERN).format(date));
		return (td != null) ? td.getTradeDay() : null;
	}

	@Override
	public TradeDay getLastTradeDay(Exchange exchange) {

		JPATradeDay td = tradeDayRepository
				.findLastTradeDayByExchange(new JPAExchange(exchange));
		return (td != null) ? new TradeDay(exchange, td.getDate(),
				td.getBusinessday()) : new TradeDay(exchange, null, 0);
	}

	@Override
	public void createTradeDay(TradeDay td) {
		tradeDayRepository.saveAndFlush(new JPATradeDay(td));

	}

	@Override
	public void incrementTradeDay(Date date, Exchange exchange) {
		tradeDayRepository.incrementBusinessdayBy1(date, new JPAExchange(
				exchange));

	}

	@Override
	public TradeDay findTradeDayBefore(TradeDay td_) {
		JPATradeDay td = tradeDayRepository.findLastTradeDayBeforeDate(
				td_.getDate(), new JPAExchange(td_.getExchange()));
		return (td == null/* Beginning of time */) ? new TradeDay(
				td_.getExchange(), td_.getDate(), 0) : new TradeDay(
				td_.getExchange(), td.getDate(), td.getBusinessday());
	}

	@Override
	public Exchange getExchange(Instrument instrument) {

		JPAExchange ex = instrumentRepository
				.getExchangeByInstrument(new JPAInstrument(instrument
						.getSymbol()));
		return (ex == null) ? null : ex.getExchange();

	}
}
