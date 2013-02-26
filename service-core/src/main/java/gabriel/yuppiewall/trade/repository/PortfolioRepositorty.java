package gabriel.yuppiewall.trade.repository;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Portfolio;

import java.util.List;

public interface PortfolioRepositorty {

	void createPortfolio(Portfolio portfolio);

	void attachIfNotpresent(Portfolio portfolio, Instrument instrument);

	List<Instrument> getPortfolioInstrument(Portfolio portfolio);

	List<Portfolio> getAllAccountPortfolio(List<Account> accountList);

	Portfolio findPortfolioByName(Account account, Portfolio portfolio);

}
