package gabriel.yuppiewall.market.service;

import gabriel.yuppiewall.market.domain.Exchange;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public abstract class MarketServiceImpl implements MarketService {

	@Override
	public Date getExchangeCurrentTime(Exchange exchange) {

		TimeZone tz = TimeZone.getTimeZone(exchange.getTimeZone());
		Calendar exLocal = new GregorianCalendar(tz);
		exLocal.setTimeInMillis(System.currentTimeMillis());
		return exLocal.getTime();
	}

}
