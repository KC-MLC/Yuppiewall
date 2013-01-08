package gabriel.yuppiewall.trade.repository;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.List;

public interface PortfolioRepositorty {

	String findPortfolioId(Portfolio portfolio);

	void createPortfolio(Portfolio portfolio);

	List<Portfolio> findAllPortfolio(PrimaryPrincipal user);

	void attachIfNotpresent(Portfolio portfolio, Instrument instrument);

	List<Instrument> getPortfolioInstrument(Portfolio portfolio);

}
