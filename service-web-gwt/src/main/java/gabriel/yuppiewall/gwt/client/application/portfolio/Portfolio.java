package gabriel.yuppiewall.gwt.client.application.portfolio;

import gabriel.yuppiewall.gwt.client.application.ApplicationWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Portfolio extends ApplicationWidget {

	interface Binder extends UiBinder<Widget, Portfolio> {
	}

	@UiField(provided = true)
	SplitLayoutPanel mainLayoutPanel = new SplitLayoutPanel(5);

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
