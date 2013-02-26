package gabriel.yuppiewall.trade.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Portfolio;

import java.util.List;

public interface PortfolioService {

	void createPortfolio(Account account, Portfolio portfolio);

	List<Instrument> getPortfolioInstrument(Portfolio portfolio);

	void attachIfNotpresent(Portfolio portfolio, Instrument instrument);

}
