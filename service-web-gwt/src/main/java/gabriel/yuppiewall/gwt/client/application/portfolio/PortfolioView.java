package gabriel.yuppiewall.gwt.client.application.portfolio;

import gabriel.yuppiewall.common.Tuple;
import gabriel.yuppiewall.gwt.client.widget.CurrencyBox;
import gabriel.yuppiewall.gwt.common.application.portfolio.AccountManagmentServiceAsync;
import gabriel.yuppiewall.gwt.common.system.SystemDataServiceAsync;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Order;
import gabriel.yuppiewall.trade.domain.Order.TransactionType;
import gabriel.yuppiewall.trade.service.AccountManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ProvidesKey;

public class PortfolioView extends Composite {

	private static PortfolioViewUiBinder uiBinder = GWT
			.create(PortfolioViewUiBinder.class);

	interface PortfolioViewUiBinder extends UiBinder<Widget, PortfolioView> {
	}

	/**
	 * The main CellTable.
	 */
	@UiField(provided = true)
	DataGrid<Transaction> portfolioDetailList;
	@UiField
	VerticalPanel lytAddNewTransaction;
	@UiField
	DisclosurePanel dpAddNewTransaction;

	private gabriel.yuppiewall.trade.domain.Portfolio selectedPortfolio;
	private InstrumentSuggestOracle oracle;

	public PortfolioView() {

		portfolioDetailList = new DataGrid<Transaction>(15,
				new ProvidesKey<Transaction>() {

					@Override
					public Object getKey(Transaction item) {
						// TODO Auto-generated method stub
						return null;
					}
				});

		initWidget(uiBinder.createAndBindUi(this));

		PortfolioApplication.AppUtils.EVENT_BUS.addHandler(GroupSelectionEvent.TYPE,
				new GroupSelectionEventHandler() {

					@Override
					public void onGroupSelectionChanged(
							GroupSelectionEvent groupSelectionEvent) {

						selectedPortfolio = groupSelectionEvent.getValue();
						initSuggestBox(groupSelectionEvent.getValue());

					}
				});

		/*
		 * Do not refresh the headers every time the data is updated. The footer
		 * depends on the current data, so we do not disable auto refresh on the
		 * footer.
		 */
		portfolioDetailList.setAutoHeaderRefreshDisabled(true);

		// Set the message to display when the table is empty.
		portfolioDetailList.setEmptyTableWidget(new Label(
				"No Active Transaction found"));

		portfolioDetailList.setMinimumTableWidth(14, Unit.EM);
		// portfolioDetailList.setMinimumTableWidth(14, Unit.EM);
		// Add a text column to show the name.
		TextColumn<Transaction> nameColumn = new TextColumn<Transaction>() {
			@Override
			public String getValue(Transaction object) {
				return null;
			}
		};
		portfolioDetailList.addColumn(nameColumn, "Name");

		// Add a text column to show the name.
		TextColumn<Transaction> date = new TextColumn<Transaction>() {
			@Override
			public String getValue(Transaction object) {
				return null;
			}
		};
		portfolioDetailList.addColumn(date, "Date");

		// Add a text column to show the name.
		TextColumn<Transaction> share = new TextColumn<Transaction>() {
			@Override
			public String getValue(Transaction object) {
				return null;
			}
		};
		portfolioDetailList.addColumn(share, "Share");

		// Add a text column to show the name.
		TextColumn<Transaction> price = new TextColumn<Transaction>() {
			@Override
			public String getValue(Transaction object) {
				return null;
			}
		};
		portfolioDetailList.addColumn(price, "price/share");

		// Add a text column to show the name.
		TextColumn<Transaction> mktprice = new TextColumn<Transaction>() {
			@Override
			public String getValue(Transaction object) {
				return null;
			}
		};
		portfolioDetailList.addColumn(mktprice, "Current Price");

		// Add a text column to show the name.
		TextColumn<Transaction> mktvalue = new TextColumn<Transaction>() {
			@Override
			public String getValue(Transaction object) {
				return null;
			}
		};
		portfolioDetailList.addColumn(mktvalue, "market value");

		// Put some text at the table's extremes. This forces the table to be
		// 4 by 3.

		/*
		 * oracle = new InstrumentSuggestOracle(); SuggestBox
		 * instrumentSuggestBox = new SuggestBox(oracle);
		 * instrumentSuggestBox.setLimit(20);
		 */

		final TextBox instrumentSuggestBox = new TextBox();
		HorizontalPanel suggestPanel = new HorizontalPanel();
		suggestPanel.setSpacing(5);
		suggestPanel.add(new Label("Symbol"));
		suggestPanel.add(instrumentSuggestBox);
		lytAddNewTransaction.add(suggestPanel);
		HorizontalPanel txDetailPanel = new HorizontalPanel();
		txDetailPanel.setSpacing(3);
		lytAddNewTransaction.add(txDetailPanel);

		txDetailPanel.add(new Label("Type"));
		final ValueListBox<Tuple<Integer, String>> vlbType = new ValueListBox<Tuple<Integer, String>>(
				new Renderer<Tuple<Integer, String>>() {
					public String render(Tuple<Integer, String> value) {
						return value.getValue();
					}

					@Override
					public void render(Tuple<Integer, String> value,
							Appendable appendable) throws IOException {
						String s = render(value);
						appendable.append(s);

					}
				});
		txDetailPanel.add(vlbType);

		txDetailPanel.add(new Label("Date"));
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("MMM dd, yyyy");
		final DateBox dateBox = new DateBox();

		dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
		txDetailPanel.add(dateBox);

		txDetailPanel.add(new Label("Price"));
		final CurrencyBox tbPrice = new CurrencyBox();
		txDetailPanel.add(tbPrice);

		txDetailPanel.add(new Label("Quantity"));
		final TextBox tbQuantity = new TextBox();
		txDetailPanel.add(tbQuantity);

		Button btAddToAccount = new Button("Add to Account");
		lytAddNewTransaction.add(btAddToAccount);
		btAddToAccount.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {

				AccountManagmentServiceAsync serviceRPC = AccountManagmentServiceAsync.Util
						.getInstance();
				Order order = null;
				try {
					order = new Order(TransactionType.getType(vlbType
							.getValue().getKey()), dateBox.getValue(), Long
							.parseLong(tbQuantity.getValue()), tbPrice
							.getValue(), selectedPortfolio.getAccount(),
							new Instrument(instrumentSuggestBox.getValue()));

					order = AccountManager.validatePlaceOrder.validate(order);
				} catch (Exception be) {
					be.printStackTrace();
				}
				serviceRPC.placeOrder(order, selectedPortfolio.getPortfolioId()
						.equals("ALL_HOLDING") ? null : selectedPortfolio,
						new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {
								caught.printStackTrace();

							}

							public void onSuccess(Void v) {

								System.out.println("FINALLY");
								dpAddNewTransaction.setOpen(false/* close */);
								
							}

						});

			}
		});

		vlbType.setValue(new Tuple<Integer, String>(1, "Buy"));
		List<Tuple<Integer, String>> types = new ArrayList<Tuple<Integer, String>>();
		types.add(new Tuple<Integer, String>(1, "Buy"));
		types.add(new Tuple<Integer, String>(2, "Sell"));
		types.add(new Tuple<Integer, String>(3, "Buy to Cover"));
		types.add(new Tuple<Integer, String>(4, "Sell Short"));
		vlbType.setAcceptableValues(types);

		// Let's put a button in the middle...
		// lytAddNewTransaction.setWidget(1, 0, new Button("Wide Button"));

		// ...and set it's column span so that it takes up the whole row.
		// lytAddNewTransaction.getFlexCellFormatter().setColSpan(1, 0, 3);
	}

	private void initSuggestBox(
			gabriel.yuppiewall.trade.domain.Portfolio portfolio) {
		SystemDataServiceAsync serviceRPC = SystemDataServiceAsync.Util
				.getInstance();
		String currencyCode = portfolio.getAccount().getCurrencyCode();
		serviceRPC.getAllInstrument(null, currencyCode,
				new AsyncCallback<List<Instrument>>() {

					public void onFailure(Throwable caught) {
						caught.printStackTrace();

					}

					public void onSuccess(List<Instrument> instruments) {

						if (instruments != null && instruments.size() > 0) {
							for (Instrument instrument : instruments) {

								oracle.add(new InstrumentMultiWordSuggestion(
										instrument));
							}
						}

					}
				});

	}
}
