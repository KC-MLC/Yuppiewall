package gabriel.yuppiewall.market.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;

import java.util.Date;

public interface MarketService {

	void createIfNotPresent(Exchange exchange, Date date);

	Date getExchangeCurrentTime(Exchange exchange);

	Exchange getExchange(Instrument instrument);

}
