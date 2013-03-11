package gabriel.yuppiewall.gwt.client.application.portfolio;

import gabriel.yuppiewall.gwt.client.application.ApplicationWidget;
import gabriel.yuppiewall.gwt.common.application.portfolio.AccountManagmentServiceAsync;
import gabriel.yuppiewall.gwt.common.application.portfolio.AccountSummary;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PortfolioApplication extends ApplicationWidget {
	public static class AppUtils {

		public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);
	}

	interface Binder extends UiBinder<Widget, PortfolioApplication> {
	}

	// System data//
	static class SystemData {
		static ArrayList<Account> accounts;
	}

	public static final SystemData SYSTEMDATA = new SystemData();

	// /

	private AccountManagmentServiceAsync accountManagmentService = AccountManagmentServiceAsync.Util
			.getInstance();

	@UiField
	Button btAddAccount;
	@UiField
	Button btManageAccount;

	@UiField
	ListBox lbGroupSelection;

	@UiField
	VerticalPanel vLytAccount;

	private HashMap<Integer, gabriel.yuppiewall.trade.domain.Portfolio> groupValues = new HashMap<Integer, gabriel.yuppiewall.trade.domain.Portfolio>();
	private int selectedIndex = -1;

	private DialogBox dAddAccount;
	private DialogBox dManageAccount;

	private gabriel.yuppiewall.trade.domain.Portfolio selectedPortfolio;

	private static final SafeHtml DESC = SafeHtmlUtils
			.fromString("PortfolioApplication Managment");

	public PortfolioApplication() {
		super("PortfolioApplication Manager", DESC);
	}

	@Override
	public Widget onInitialize() {

		// Create the UiBinder.
		Binder uiBinder = GWT.create(Binder.class);
		Widget widget = uiBinder.createAndBindUi(this);
		loadpage();
		return widget;

	}

	private void loadpage() {

		// initializeAccountSummarysection
		initializeAccountSummarySection();
		// InitiliazegroupSection
		initiliazeGroupSection();

	}

	private void initiliazeGroupSection() {

		// Set up the callback object.
		AsyncCallback<ArrayList<Account>> callback = new AsyncCallback<ArrayList<Account>>() {
			public void onFailure(Throwable caught) {
				caught.printStackTrace();

			}

			public void onSuccess(ArrayList<Account> result) {
				System.out.println("GETTING ACCOUNT >>" + result);
				if (result == null)
					return;
				SystemData.accounts = null;
				SystemData.accounts = result;
				for (int i = 0, index = 0; i < result.size(); i++) {
					Account account = result.get(i);
					int itemNumber = index++;
					lbGroupSelection.insertItem(account.getAccountName(),
							itemNumber);

					gabriel.yuppiewall.trade.domain.Portfolio allHoldingPortfolio = new gabriel.yuppiewall.trade.domain.Portfolio(
							"ALL_HOLDING");

					allHoldingPortfolio.setAccount(account);
					groupValues.put(itemNumber, allHoldingPortfolio);

					List<gabriel.yuppiewall.trade.domain.Portfolio> portfolios = account
							.getPortfolios();
					/*
					 * groupSelection.getElement().getElementsByTagName("option")
					 * .getItem(itemNumber) .setAttribute("disabled",
					 * "disabled");
					 */
					for (gabriel.yuppiewall.trade.domain.Portfolio portfolio : portfolios) {
						itemNumber = index++;
						lbGroupSelection.insertItem(
								"-----" + portfolio.getPortfolioName(),
								itemNumber);
						portfolio.setAccount(account);
						groupValues.put(itemNumber, portfolio);
					}
				}

				lbGroupSelection.setSelectedIndex(0);
				onChange(null);
			}
		};

		// Make the call to the stock price service.
		accountManagmentService.getAccountPortfolioList(new PrimaryPrincipal(
				"khushboo.choudhary@gmail.com"), callback);

	}

	private void initializeAccountSummarySection() {

		// Set up the callback object.
		AsyncCallback<AccountSummary[]> callback = new AsyncCallback<AccountSummary[]>() {
			public void onFailure(Throwable caught) {
				caught.printStackTrace();

			}

			public void onSuccess(AccountSummary[] result) {
				if (result == null)
					return;
				for (AccountSummary accountSummary : result) {
					vLytAccount.add(new AccountSummaryView(accountSummary));
				}
			}
		};

		// Make the call to the stock price service.

		accountManagmentService.getAccountSummaryCurrencyWise(
				new PrimaryPrincipal("khushboo.choudhary@gmail.com"), callback);

	}

	@Override
	protected void asyncOnInitialize(final AsyncCallback<Widget> callback) {
		GWT.runAsync(PortfolioApplication.class, new RunAsyncCallback() {

			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}

			public void onSuccess() {
				callback.onSuccess(onInitialize());
			}
		});
	}

	@UiHandler("lbGroupSelection")
	void onChange(ChangeEvent event) {
		int temp = lbGroupSelection.getSelectedIndex();
		if (temp == selectedIndex)
			return;
		selectedIndex = temp;
		selectedPortfolio = groupValues.get(selectedIndex);

		AppUtils.EVENT_BUS
				.fireEvent(new GroupSelectionEvent(selectedPortfolio));

	}

	@UiHandler("btManageAccount")
	void onBtManageAccountClick(ClickEvent event) {
		if (dManageAccount == null) {
			dManageAccount = new DialogBox();
			dManageAccount.ensureDebugId("dManageAccount");
			dManageAccount.setGlassEnabled(true);
			// dManageAccount.setAnimationEnabled(true);
			dManageAccount.setModal(true);

			dManageAccount.setWidget(new ManageAccountView() {
				@Override
				public void dispose() {
					super.dispose();
					dManageAccount.hide();
					dManageAccount = null;
				}
			});
			dManageAccount.setSize("450px", "200px");
		}
		dManageAccount.center();
		dManageAccount.show();
	}

	@UiHandler("btAddAccount")
	void onButtonClick(ClickEvent event) {
		if (dAddAccount == null) {
			dAddAccount = new DialogBox();
			dAddAccount.ensureDebugId("dAddAccount");
			dAddAccount.setGlassEnabled(true);
			// dAddAccount.setAnimationEnabled(true);
			dAddAccount.setModal(true);

			dAddAccount.setWidget(new AddAccount() {
				@Override
				public void dispose() {
					super.dispose();
					dAddAccount.hide();
					dAddAccount = null;
				}
			});
			dAddAccount.setSize("300px", "300px");
		}
		dAddAccount.center();
		dAddAccount.show();
	}
}
