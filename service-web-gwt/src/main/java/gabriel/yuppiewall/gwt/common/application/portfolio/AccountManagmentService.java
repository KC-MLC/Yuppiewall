package gabriel.yuppiewall.gwt.common.application.portfolio;

import java.util.List;

import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("accountManagmentService")
public interface AccountManagmentService extends RemoteService {

	void createAccount(Account account);

	List<Account> getAccountPortfolioList(PrimaryPrincipal principal);
	
	
}
