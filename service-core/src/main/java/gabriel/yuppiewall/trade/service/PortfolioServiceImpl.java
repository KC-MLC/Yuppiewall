package gabriel.yuppiewall.trade.service;

import gabriel.yuppiewall.common.exception.EntityAlreadyExistsException;
import gabriel.yuppiewall.common.exception.MissingRequiredFiledException;
import gabriel.yuppiewall.common.util.ValidationUtil;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.repository.PortfolioRepositorty;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.Date;
import java.util.List;

public abstract class PortfolioServiceImpl implements PortfolioService {

	@Override
	public Portfolio createPortfolio(Portfolio portfolio) {

		List<String> error = ValidationUtil.vallidate(portfolio);
		if (error != null)
			throw new MissingRequiredFiledException(Portfolio.class, error,
					null);

		// check if name is unique
		String portfolioId = getPortfolioRepositorty().findPortfolioId(
				portfolio);
		if (portfolioId != null)
			throw new EntityAlreadyExistsException(Portfolio.class,
					"portfolioName", null);

		// create a new portfolio
		portfolio.setCreationtDate(new Date());
		portfolioId = portfolio.getCreationtDate().getTime() + ":"
				+ portfolio.getUser().getName();
		portfolio.setPortfolioId(portfolioId);
		getPortfolioRepositorty().createPortfolio(portfolio);
		portfolioId = getPortfolioRepositorty().findPortfolioId(portfolio);
		portfolio.setPortfolioId(portfolioId);
		return portfolio;
	}

	@Override
	public List<Portfolio> getPortfolio(PrimaryPrincipal user) {

		return getPortfolioRepositorty().findAllPortfolio(user);
	}

	protected abstract PortfolioRepositorty getPortfolioRepositorty();

	@Override
	public void attachIfNotpresent(Portfolio portfolio, Instrument instrument) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"method not impl attachIfNotpresent ");
	}
}
