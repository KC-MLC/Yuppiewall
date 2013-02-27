package gabriel.yuppiewall.gwt.client.application.portfolio;

import gabriel.yuppiewall.common.Tuple;
import gabriel.yuppiewall.gwt.client.application.ApplicationWidget;
import gabriel.yuppiewall.gwt.common.application.portfolio.AccountManagmentServiceAsync;
import gabriel.yuppiewall.gwt.common.application.portfolio.AccountSummary;
import gabriel.yuppiewall.gwt.common.application.portfolio.PortfolioServiceAsync;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

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

public class Portfolio extends ApplicationWidget {
	public static class AppUtils {

		public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);
	}

	interface Binder extends UiBinder<Widget, Portfolio> {
	}

	private AccountManagmentServiceAsync accountManagmentService = AccountManagmentServiceAsync.Util
			.getInstance();

	private PortfolioServiceAsync portfolioService = PortfolioServiceAsync.Util
			.getInstance();

	@UiField
	Button btAddAccount;

	@UiField
	ListBox lbGroupSelection;

	@UiField
	VerticalPanel vLytAccount;

	private HashMap<Integer, Tuple<Integer, String>> groupValues = new HashMap<Integer, Tuple<Integer, String>>();
	private int selectedIndex = -1;

	private DialogBox dAddAccount;

	private Account selectedAccount;

	private gabriel.yuppiewall.trade.domain.Portfolio selectedPortfolio;

	private static final SafeHtml DESC = SafeHtmlUtils
			.fromString("Portfolio Managment");

	public Portfolio() {
		super("Portfolio Manager", DESC);
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
		InitiliazeGroupSection();

	}

	private void InitiliazeGroupSection() {

		// Set up the callback object.
		AsyncCallback<List<Account>> callback = new AsyncCallback<List<Account>>() {
			public void onFailure(Throwable caught) {
				caught.printStackTrace();

			}

			public void onSuccess(List<Account> result) {
				System.out.println("GETTING ACCOUNT >>" + result);
				if (result == null)
					return;

				for (int i = 0, index = 0; i < result.size(); i++) {
					Account account = result.get(i);
					int itemNumber = index++;
					lbGroupSelection.insertItem(account.getAccountName(),
							itemNumber);

					groupValues.put(itemNumber, new Tuple<Integer, String>(1,
							account.getAccountId()));

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
						groupValues.put(itemNumber, new Tuple<Integer, String>(
								2, portfolio.getPortfolioId()));
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

		portfolioService.getAccountSummaryCurrencyWise(new PrimaryPrincipal(
				"khushboo.choudhary@gmail.com"), callback);

	}

	@Override
	protected void asyncOnInitialize(final AsyncCallback<Widget> callback) {
		GWT.runAsync(Portfolio.class, new RunAsyncCallback() {

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
		Tuple<Integer, String> value = groupValues.get(selectedIndex);
		selectedAccount = null;
		selectedPortfolio = null;
		switch (value.getKey().intValue()) {
		case 1:
			selectedAccount = new Account(value.getValue());
			break;
		case 2:
			selectedPortfolio = new gabriel.yuppiewall.trade.domain.Portfolio(
					value.getValue());
		default:
			// TODO disaster
		}
		AppUtils.EVENT_BUS.fireEvent(new GroupSelectionEvent(value));

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
