package gabriel.yuppiewall.gwt.server.application.portfolio;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import gabriel.yuppiewall.gwt.common.application.portfolio.AccountManagmentService;
import gabriel.yuppiewall.trade.domain.Account;

@SuppressWarnings("serial")
public class AccountManagmentServlet extends RemoteServiceServlet implements
		AccountManagmentService {

	@Override
	public void createAccount(Account account) {
		System.out.println("CALLED");

	}

}
