package gabriel.yuppiewall.gwt.common.application.portfolio;

import gabriel.yuppiewall.trade.domain.Account;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("accountManagmentService")
public interface AccountManagmentService extends RemoteService {

	void createAccount(Account account);
}
