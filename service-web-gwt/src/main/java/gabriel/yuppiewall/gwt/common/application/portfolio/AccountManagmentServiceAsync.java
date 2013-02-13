package gabriel.yuppiewall.gwt.common.application.portfolio;

import gabriel.yuppiewall.trade.domain.Account;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AccountManagmentServiceAsync {

	void createAccount(Account account, AsyncCallback<Void> callback);	

}
