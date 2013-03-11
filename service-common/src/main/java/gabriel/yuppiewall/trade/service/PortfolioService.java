package gabriel.yuppiewall.trade.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Portfolio;

import java.util.List;

public interface PortfolioService {

	List<Instrument> getPortfolioInstrument(Portfolio portfolio);

	void attachIfNotpresent(Portfolio portfolio, Instrument instrument);

	void createPortfolio(Portfolio portfolio);

}
