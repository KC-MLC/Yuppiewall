package gabriel.yuppiewall.market.service;

import gabriel.yuppiewall.market.domain.Exchange;

import java.util.Date;

public interface MarketService {

	Date getExchangeCurrentTime(Exchange exchange);

}
