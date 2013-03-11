package gabriel.yuppiewall.gwt.server.application.portfolio;

import gabriel.yuppiewall.common.exception.BusinessException;
import gabriel.yuppiewall.gwt.common.application.portfolio.AccountManagmentService;
import gabriel.yuppiewall.gwt.common.application.portfolio.AccountSummary;
import gabriel.yuppiewall.gwt.common.application.portfolio.PortfolioSummary;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Order;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.service.AccountManager;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class AccountManagmentServlet extends RemoteServiceServlet implements
		AccountManagmentService {
	private WebApplicationContext appContext;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		appContext = WebApplicationContextUtils
				.getWebApplicationContext(servletConfig.getServletContext());
	}

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

	@Override
	public void createAccount(Account account) {
		try {

			AccountManager accountManager = (AccountManager) appContext
					.getBean("accountManager");
			accountManager.createAccount(account);
		} catch (BusinessException be) {
			throw be;
		} catch (Exception e) {
			System.out.println("EROOOOOOOOOOOOO");
			e.printStackTrace();
		}

	}

	@Override
	public ArrayList<Account> getAccountPortfolioList(PrimaryPrincipal principal) {
		try {

			AccountManager accountManager = (AccountManager) appContext
					.getBean("accountManager");
			return (ArrayList<Account>) accountManager
					.getAccountPortfolioList(principal);

		} catch (BusinessException be) {
			throw be;
		} catch (Exception e) {
			System.out.println("EROOOOOOOOOOOOO");
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void createPortfolio(Portfolio portfolio) {
		try {

			gabriel.yuppiewall.trade.service.PortfolioService portfolioService = (gabriel.yuppiewall.trade.service.PortfolioService) appContext
					.getBean("portfolioService");
			portfolioService.createPortfolio( portfolio);
		} catch (BusinessException be) {
			throw be;
		} catch (Exception e) {
			System.out.println("EROOOOOOOOOOOOO");
			e.printStackTrace();
		}
	}

	@Override
	public void placeOrder(Order order, Portfolio portfolio) {
		try {

			AccountManager accountManager = (AccountManager) appContext
					.getBean("accountManager");
			accountManager.placeOrder(order, portfolio);

		} catch (BusinessException be) {
			throw be;
		} catch (Exception e) {
			System.out.println("EROOOOOOOOOOOOO");
			e.printStackTrace();

		}

	}

}
