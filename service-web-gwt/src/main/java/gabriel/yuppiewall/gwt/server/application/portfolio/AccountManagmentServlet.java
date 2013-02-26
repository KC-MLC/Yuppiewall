package gabriel.yuppiewall.gwt.server.application.portfolio;

import java.util.List;

import gabriel.yuppiewall.common.exception.BusinessException;
import gabriel.yuppiewall.gwt.common.application.portfolio.AccountManagmentService;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.service.AccountManager;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

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
	public List<Account> getAccountPortfolioList(PrimaryPrincipal principal) {
		try {

			AccountManager accountManager = (AccountManager) appContext
					.getBean("accountManager");
			return accountManager.getAccountPortfolioList(principal);

		} catch (BusinessException be) {
			throw be;
		} catch (Exception e) {
			System.out.println("EROOOOOOOOOOOOO");
			e.printStackTrace();
			return null;
		}

	}

}
