package gabriel.yuppiewall.gwt.common.application.portfolio;

import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Order;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("accountManagmentService")
public interface AccountManagmentService extends RemoteService {

	void createAccount(Account account);

	ArrayList<Account> getAccountPortfolioList(PrimaryPrincipal principal);
	void placeOrder(Order order, Portfolio portfolio);

	AccountSummary[] getAccountSummaryCurrencyWise(PrimaryPrincipal subject);

	void createPortfolio(Portfolio portfolio);
	
}
