package gabriel.yuppiewall.gwt.client.application.portfolio;

import gabriel.yuppiewall.gwt.client.application.Component;
import gabriel.yuppiewall.gwt.common.application.portfolio.AccountManagmentService;
import gabriel.yuppiewall.gwt.common.application.portfolio.AccountManagmentServiceAsync;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Portfolio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class ManageAccountView extends Composite implements Component {

	private static ManageAccountViewUiBinder uiBinder = GWT
			.create(ManageAccountViewUiBinder.class);
	private AccountManagmentServiceAsync svcAccountManagment = GWT
			.create(AccountManagmentService.class);
	private static final List<String> DAYS = Arrays.asList("Sunday", "Monday",
			"Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

	interface ManageAccountViewUiBinder extends
			UiBinder<Widget, ManageAccountView> {
	}

	@UiField
	ListBox accountList;
	@UiField(provided = true)
	CellList<Portfolio> portfolioList;
	private int selectedIndex = -1;

	/*
	 * @UiField(provided = true) CellBrowser portfolioBrowser;
	 * 
	 * @UiField(provided = true) CellBrowser addedHoldingBrowser;
	 * 
	 * @UiField(provided = true) CellBrowser allHoldingBrowser;
	 */

	/**
	 * A custom {@link Cell} used to render a {@link Contact}.
	 */
	private static class PortfolioCell extends AbstractCell<Portfolio> {

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context arg0,
				Portfolio value, SafeHtmlBuilder sb) {
			if (value != null) {
				sb.appendEscaped(value.getPortfolioName());
			}

		}
	}

	public ManageAccountView() {
		portfolioList = new CellList<Portfolio>(new PortfolioCell());
		portfolioList
				.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// Add a selection model to handle user selection.
		final SingleSelectionModel<Portfolio> selectionModelPortfolio = new SingleSelectionModel<Portfolio>();
		portfolioList.setSelectionModel(selectionModelPortfolio);
		selectionModelPortfolio
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						Portfolio selected = selectionModelPortfolio
								.getSelectedObject();
						if (selected != null) {
							Window.alert("You selected: " + selected);
						}
					}
				});

		initWidget(uiBinder.createAndBindUi(this));
		initPage();
	}

	private void initPage() {
		ArrayList<Account> accounts = PortfolioApplication.SYSTEMDATA.accounts;
		if (accounts == null || accounts.size() == 0)
			return;
		for (int i = 0; i < accounts.size(); i++) {
			Account account = accounts.get(i);
			accountList.insertItem(account.getAccountName(), i);

		}
		accountList.setSelectedIndex(0);

	}

	@UiHandler("accountList")
	void onChange(ChangeEvent event) {
		int temp = accountList.getSelectedIndex();

		if (temp == selectedIndex)
			return;
		selectedIndex = temp;

		Account selectedAccount = PortfolioApplication.SYSTEMDATA.accounts
				.get(selectedIndex);

		// Set the total row count. This isn't strictly necessary, but it
		// affects
		// paging calculations, so its good habit to keep the row count up to
		// date.
		List<gabriel.yuppiewall.trade.domain.Portfolio> portfolios = selectedAccount
				.getPortfolios();

		// Push the data into the widget.
		portfolioList.setRowData(portfolios);
	}

	@UiHandler("btAddNewPortfolio")
	void onAddNew(ClickEvent event) {

		final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
		simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
		simplePopup.setWidth("150px");
		HorizontalPanel newFolioPanel = new HorizontalPanel();
		final TextBox tbPortfolioName = new TextBox();
		newFolioPanel.add(tbPortfolioName);
		Button btAddNew = new Button("Save");
		btAddNew.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				AccountManagmentServiceAsync serviceRPC = AccountManagmentServiceAsync.Util
						.getInstance();

				Account selectedAccount = PortfolioApplication.SYSTEMDATA.accounts
						.get(selectedIndex);

				Portfolio portfolio = new Portfolio(selectedAccount,
						tbPortfolioName.getText());

				serviceRPC.createPortfolio(portfolio,
						new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {
								caught.printStackTrace();
								Window.alert("PROBLEM");
							}

							public void onSuccess(Void v) {

								System.out.println("FINALLY");
								Window.alert("DONE");

							}

						});
			}
		});
		newFolioPanel.add(btAddNew);
		simplePopup.setWidget(newFolioPanel);

		Widget source = (Widget) event.getSource();
		int left = source.getAbsoluteLeft() + 10;
		int top = source.getAbsoluteTop() + 10;
		simplePopup.setPopupPosition(left, top);

		// Show the popup
		simplePopup.show();
	}

	public void dispose() {

	}
}
