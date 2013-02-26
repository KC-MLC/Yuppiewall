/**
 * 
 */
package gabriel.yuppiewall.gwt.client.application.portfolio;

import gabriel.yuppiewall.gwt.client.application.Component;
import gabriel.yuppiewall.gwt.common.application.portfolio.AccountManagmentService;
import gabriel.yuppiewall.gwt.common.application.portfolio.AccountManagmentServiceAsync;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.service.AccountManager;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.NotificationMole;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author parvez
 * 
 */
public class AddAccount extends Composite implements Component {

	private static AddAccountUiBinder uiBinder = GWT
			.create(AddAccountUiBinder.class);
	private AccountManagmentServiceAsync svcAccountManagment = GWT
			.create(AccountManagmentService.class);

	interface AddAccountUiBinder extends UiBinder<Widget, AddAccount> {
	}

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 * xmlns:g="urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	public AddAccount() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	TextBox tfAccountName;
	@UiField
	ListBox lbCurrencyType;

	@UiHandler("btAddAccount")
	void onButtonClick(ClickEvent event) {

		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				caught.printStackTrace();

			}

			public void onSuccess(Void result) {

				NotificationMole nm = new NotificationMole();
				nm.setAnimationDuration(2000);
				nm.setTitle("Title");
				nm.setHeight("100px");
				nm.setWidth("200px");
				nm.setMessage("Test message to be shown in mole");
				nm.show();

				// dispose();
			}
		};

		// Make the call to the stock price service.

		Account account = new Account();
		account.setAccountName(tfAccountName.getText());
		account.setClient(new PrimaryPrincipal("khushboo.choudhary@gmail.com"));
		int index = lbCurrencyType.getSelectedIndex();
		String currencyCode = lbCurrencyType.getItemText(index);
		account.setCurrencyCode(currencyCode);
		account = AccountManager.validateCreateAccount.validate(account);
		System.out.println(account);
		svcAccountManagment.createAccount(account, callback);
	}

	@UiHandler("btClose")
	void onClose(ClickEvent event) {

		dispose();
	}

	public void dispose() {

	}
}
