package gabriel.yuppiewall.market.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange_;

import java.util.Date;

public interface MarketService {

	void createIfNotPresent(Exchange_ exchange, Date date);

	Date getExchangeCurrentTime(Exchange_ exchange);

	Exchange_ getExchange(Instrument instrument);

}
