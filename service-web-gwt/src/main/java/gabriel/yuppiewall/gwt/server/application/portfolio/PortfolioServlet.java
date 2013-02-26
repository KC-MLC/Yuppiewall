package gabriel.yuppiewall.gwt.server.application.portfolio;

import gabriel.yuppiewall.gwt.common.application.portfolio.AccountSummary;
import gabriel.yuppiewall.gwt.common.application.portfolio.PortfolioService;
import gabriel.yuppiewall.gwt.common.application.portfolio.PortfolioSummary;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PortfolioServlet extends RemoteServiceServlet implements
		PortfolioService {

	@Override
	public AccountSummary[] getAccountSummaryCurrencyWise(
			PrimaryPrincipal subject) {
		AccountSummary account = new AccountSummary("USD", 23456);
		PortfolioSummary ps[] = new PortfolioSummary[] {
				new PortfolioSummary("P1", 234),
				new PortfolioSummary("P2", 234) };
		account.setPortFolioSummary(ps);

		return new AccountSummary[] { account };
	}

}
