package gabriel.yuppiewall.gwt.client.application.portfolio;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
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

	static class PortfolioSummaryCell extends AbstractCell<PortfolioSummary> {

		@Override
		public void render(Context context, PortfolioSummary value,
				SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
				return;
			}

			sb.appendHtmlConstant("<table>");

			// Add the contact image.
			sb.appendHtmlConstant("<tr><td rowspan='3'>");
			sb.appendEscaped("kc1");
			sb.appendHtmlConstant("</td>");

			// Add the name and address.
			sb.appendHtmlConstant("<tr>");
			sb.appendHtmlConstant("<td style='font-size:95%;'>");
			sb.appendEscaped("$657");
			sb.appendHtmlConstant("</td>");
			sb.appendEscaped("$2.50 (0.4%)");
			sb.appendHtmlConstant("</td></tr>");

			// Add the contact image.
			sb.appendHtmlConstant("<tr><td rowspan='3'>");
			sb.appendEscaped("kc2");
			sb.appendHtmlConstant("</td>");

			// Add the name and address.
			sb.appendHtmlConstant("<tr>");
			sb.appendHtmlConstant("<td style='font-size:95%;'>");
			sb.appendEscaped("$1000");
			sb.appendHtmlConstant("</td>");
			sb.appendEscaped("$3.50 (0.4%)");
			sb.appendHtmlConstant("</td></tr>");
			sb.appendHtmlConstant("</table>");
		}
	}

	/**
	 * The CellList.
	 */
	private CellList<PortfolioSummary> clPortfolioSummary;
	/**
	 * The key provider that provides the unique ID of a contact.
	 */
	public static final ProvidesKey<PortfolioSummary> KEY_PROVIDER = new ProvidesKey<PortfolioSummary>() {
		@Override
		public Object getKey(PortfolioSummary item) {
			return item == null ? null : item.getId();
		}
	};

	public AccountSummaryView() {
		initWidget(uiBinder.createAndBindUi(this));
		PortfolioSummaryCell portfolioSummaryCell = new PortfolioSummaryCell();
		clPortfolioSummary = new CellList<PortfolioSummary>(
				portfolioSummaryCell, KEY_PROVIDER);
		accountList.add(clPortfolioSummary);
		dataProvider.addDataDisplay(clPortfolioSummary);
		List<PortfolioSummary> list = dataProvider.getList();
		list.add(new PortfolioSummary());

	}
}
