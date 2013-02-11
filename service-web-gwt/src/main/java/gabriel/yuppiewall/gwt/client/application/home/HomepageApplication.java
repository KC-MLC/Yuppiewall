package gabriel.yuppiewall.gwt.client.application.home;

import gabriel.yuppiewall.gwt.client.application.ApplicationWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HomepageApplication extends ApplicationWidget {

	private static final SafeHtml DESC = SafeHtmlUtils.fromString("Dashboard");

	public HomepageApplication() {
		super("Dashboard", DESC);
	}

	@Override
	public Widget onInitialize() {

		// Create a Vertical Panel
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(5);

		// Add some content to the panel
		for (int i = 1; i < 10; i++) {
			vPanel.add(new Button("B1" + " " + i));
		}

		// Return the content
		vPanel.ensureDebugId("cwVerticalPanel");
		return vPanel;
	}

	@Override
	protected void asyncOnInitialize(final AsyncCallback<Widget> callback) {
		GWT.runAsync(HomepageApplication.class, new RunAsyncCallback() {

			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}

			public void onSuccess() {
				callback.onSuccess(onInitialize());
			}
		});
	}
}
