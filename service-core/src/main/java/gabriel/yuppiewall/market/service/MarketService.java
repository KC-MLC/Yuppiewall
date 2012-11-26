package gabriel.yuppiewall.market.service;

import gabriel.yuppiewall.market.domain.Exchange_;

import java.util.Date;

public interface MarketService {

	void createIfNotPresent(Exchange_ exchange, Date date);

}
