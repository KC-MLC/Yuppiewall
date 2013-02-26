package gabriel.yuppiewall.gwt.client.application.portfolio;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
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
	// @UiField
	FlexTable lytAddNewTransaction;

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
		if (lytAddNewTransaction == null)
			return;
		lytAddNewTransaction.setText(0, 0, "Symbol");
		lytAddNewTransaction.setWidget(2, 3, new TextBox());
		lytAddNewTransaction.setWidget(3, 0, new Button("Add to Account"));

		lytAddNewTransaction.setWidget(0, 1, new TextBox());

		lytAddNewTransaction.setText(1, 0, "Type");
		lytAddNewTransaction.setWidget(2, 0, new TextBox());

		lytAddNewTransaction.setText(1, 1, "Date");
		lytAddNewTransaction.setWidget(2, 1, new TextBox());

		lytAddNewTransaction.setText(1, 2, "Price");
		lytAddNewTransaction.setWidget(2, 2, new TextBox());

		lytAddNewTransaction.setText(1, 3, "Quantity");

		// Let's put a button in the middle...
		// lytAddNewTransaction.setWidget(1, 0, new Button("Wide Button"));

		// ...and set it's column span so that it takes up the whole row.
		// lytAddNewTransaction.getFlexCellFormatter().setColSpan(1, 0, 3);
	}
}
