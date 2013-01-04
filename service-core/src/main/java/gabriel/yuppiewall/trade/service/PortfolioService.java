package gabriel.yuppiewall.trade.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Portfolio;

public interface PortfolioService {

	void attachIfNotpresent(Portfolio portfolio, Instrument instrument);

}
