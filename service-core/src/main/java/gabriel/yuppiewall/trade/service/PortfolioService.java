package gabriel.yuppiewall.trade.service;

import java.util.List;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

public interface PortfolioService {

	Portfolio createPortfolio(Portfolio portfolio);

	List<Portfolio> getPortfolio(PrimaryPrincipal user);

	List<Instrument> getPortfolioInstrument(Portfolio portfolio);

	void attachIfNotpresent(Portfolio portfolio, Instrument instrument);

}
