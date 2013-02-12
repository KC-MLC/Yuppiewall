package gabriel.yuppiewall.gwt.client.application.portfolio;

import gabriel.yuppiewall.gwt.client.application.ApplicationWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class Portfolio extends ApplicationWidget {

	interface Binder extends UiBinder<Widget, Portfolio> {
	}

	@UiField(provided = true)
	SplitLayoutPanel mainLayoutPanel = new SplitLayoutPanel(5);

	/**
	 * The main CellTable.
	 */
	@UiField(provided = true)
	DataGrid<Transaction> portfolioDetailList;
	@UiField
	Button btAddAccount;

	@UiField
	VerticalPanel vLytAccount;

	private DialogBox dAddAccount;

	private static final SafeHtml DESC = SafeHtmlUtils
			.fromString("Portfolio Managment");

	public Portfolio() {
		super("Portfolio Manager", DESC);
	}

	@Override
	public Widget onInitialize() {

		portfolioDetailList = new DataGrid<Transaction>(15,
				new ProvidesKey<Transaction>() {

					@Override
					public Object getKey(Transaction item) {
						// TODO Auto-generated method stub
						return null;
					}
				});

		portfolioDetailList.setMinimumTableWidth(140, Unit.EM);
		
	
		// Create the UiBinder.
		Binder uiBinder = GWT.create(Binder.class);
		Widget widget = uiBinder.createAndBindUi(this);
		vLytAccount.add(new AccountSummaryView());
		vLytAccount.add(new AccountSummaryView());
		return widget;

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

	@UiHandler("btAddAccount")
	void onButtonClick(ClickEvent event) {
		if (dAddAccount == null) {
			dAddAccount = new DialogBox();
			dAddAccount.ensureDebugId("dAddAccount");
			dAddAccount.setGlassEnabled(true);
			dAddAccount.setAnimationEnabled(true);
			dAddAccount.setModal(true);

			dAddAccount.setWidget(new AddAccount());
			dAddAccount.setSize("300px", "300px");
		}
		dAddAccount.center();
		dAddAccount.show();
	}
}
