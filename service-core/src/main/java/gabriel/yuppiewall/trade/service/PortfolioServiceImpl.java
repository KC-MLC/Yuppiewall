package gabriel.yuppiewall.trade.service;

import gabriel.yuppiewall.common.exception.EntityAlreadyExistsException;
import gabriel.yuppiewall.common.util.ValidationUtil;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.repository.PortfolioRepositorty;

import java.util.Date;
import java.util.List;

public abstract class PortfolioServiceImpl implements PortfolioService {

	@Override
	public void createPortfolio(Account account, Portfolio portfolio) {

		// Validate Account
		account = ValidationUtil.notNull(account, "account should be valid");
		account.setAccountId(ValidationUtil.notNull(account.getAccountId(),
				"account should be valid"));

		// Validate Portfolio
		portfolio = ValidationUtil.notNull(portfolio,
				"portfolio should be valid");
		portfolio.setPortfolioName(ValidationUtil.notNull(
				portfolio.getPortfolioName(), "portfolio should be valid"));
		// check if name is unique
		Portfolio temp = getPortfolioRepositorty().findPortfolioByName(account,
				portfolio);
		if (temp != null)
			throw new EntityAlreadyExistsException(
					"portfolio name already in use");

		// create a new portfolio
		portfolio.setCreationtDate(new Date());

		getPortfolioRepositorty().createPortfolio(portfolio);

	}

	@Override
	public List<Instrument> getPortfolioInstrument(Portfolio portfolio) {

		return getPortfolioRepositorty().getPortfolioInstrument(portfolio);
	}

	protected abstract PortfolioRepositorty getPortfolioRepositorty();

	@Override
	public void attachIfNotpresent(Portfolio portfolio, Instrument instrument) {
		getPortfolioRepositorty().attachIfNotpresent(portfolio, instrument);
	}

}
