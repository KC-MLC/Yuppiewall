package gabriel.yuppiewall.gwt.client.application.portfolio;

import gabriel.yuppiewall.gwt.common.application.portfolio.AccountSummary;
import gabriel.yuppiewall.gwt.common.application.portfolio.PortfolioSummary;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

public class AccountSummaryView extends Composite {

	private static AccountSummaryViewUiBinder uiBinder = GWT
			.create(AccountSummaryViewUiBinder.class);

	interface AccountSummaryViewUiBinder extends
			UiBinder<Widget, AccountSummaryView> {
	}

	/**
	 * The provider that holds the list of contacts in the database.
	 */
	private ListDataProvider<PortfolioSummary> dataProvider = new ListDataProvider<PortfolioSummary>();
	@UiField
	VerticalPanel accountList;

	@UiField
	Label laAccountTotal;

	@UiField
	Label laAccountTotalChange;

	static class PortfolioSummaryCell extends AbstractCell<PortfolioSummary> {

		@Override
		public void render(Context context, PortfolioSummary value,
				SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
				return;
			}

			sb.appendHtmlConstant("<table width='100%'>");

			// Add the contact image.
			sb.appendHtmlConstant("<tr><td align='left' rowspan='2'>");
			sb.appendEscaped(value.getName());
			sb.appendHtmlConstant("</td>");

			sb.appendHtmlConstant("<td align='right' style='font-size:95%;'>");
			sb.appendEscaped(numberFormat.format(value.getTotal()));
			sb.appendHtmlConstant("</td></tr>");

			sb.appendHtmlConstant("<tr>");
			sb.appendHtmlConstant("<td align='right' style='font-size:95%;'>");
			sb.appendEscaped("$2.50 (0.4%)");
			sb.appendHtmlConstant("</td></tr>");

			sb.appendHtmlConstant("</table>");
		}
	}

	/**
	 * The CellList.
	 */
	private CellList<PortfolioSummary> clPortfolioSummary;
	private static NumberFormat numberFormat;
	/**
	 * The key provider that provides the unique ID of a contact.
	 */
	public static final ProvidesKey<PortfolioSummary> KEY_PROVIDER = new ProvidesKey<PortfolioSummary>() {
		@Override
		public Object getKey(PortfolioSummary item) {
			return item == null ? null : item.getName();
		}
	};

	public AccountSummaryView() {
		initWidget(uiBinder.createAndBindUi(this));
		PortfolioSummaryCell portfolioSummaryCell = new PortfolioSummaryCell();
		clPortfolioSummary = new CellList<PortfolioSummary>(
				portfolioSummaryCell, KEY_PROVIDER);

		accountList.add(clPortfolioSummary);
		dataProvider.addDataDisplay(clPortfolioSummary);

	}

	public AccountSummaryView(AccountSummary accountSummary) {
		this();
		numberFormat = NumberFormat.getCurrencyFormat(accountSummary
				.getCurrencyCode());
		laAccountTotal.setText(numberFormat.format(accountSummary.getTotal()));

		PortfolioSummary[] pfs = accountSummary.getPortFolioSummary();
		if (pfs == null)
			return;
		List<PortfolioSummary> list = dataProvider.getList();
		for (PortfolioSummary portfolioSummary : pfs) {
			list.add(portfolioSummary);
		}

	}
}
