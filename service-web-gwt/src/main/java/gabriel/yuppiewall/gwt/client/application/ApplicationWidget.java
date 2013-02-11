package gabriel.yuppiewall.gwt.client.application;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class ApplicationWidget extends SimpleLayoutPanel {

	/**
	 * Generic callback used for asynchronously loaded data.
	 * 
	 * @param <T>
	 *            the data type
	 */

	public static interface Callback<T> {
		void onError();

		void onSuccess(T value);
	}

	/**
	 * The name of the Application.
	 */
	private final String name;

	/**
	 * Whether the demo widget has been initialized.
	 */
	private boolean applicationInitialized;

	/**
	 * Whether the demo widget is (asynchronously) initializing.
	 */
	private boolean applicationInitializing;

	private SafeHtml description;

	public ApplicationWidget(String name, SafeHtml description) {
		this.name = name;
		this.description = description;

	}

	/**
	 * Get the description of this example.
	 * 
	 * @return a description for this example
	 */
	public final SafeHtml getDescription() {
		return description;
	}

	/**
	 * Get the name of this example to use as a title.
	 * 
	 * @return a name for this example
	 */
	public final String getName() {
		return name;
	}

	public abstract Widget onInitialize();

	/**
	 * The view that holds Application.
	 */
	private ApplicationWidgetView view;

	/**
	 * Called when initialization has completed and the widget has been added to
	 * the page.
	 */
	public void onInitializeComplete() {
	}

	protected abstract void asyncOnInitialize(
			final AsyncCallback<Widget> callback);

	@Override
	protected void onLoad() {
		if (view == null) {
			view = new ApplicationWidgetView();
			view.setName(getName());
			setWidget(view);
		}
		ensureApplicationInitialized();
		super.onLoad();
	}

	/**
	 * Ensure that the demo widget has been initialized. Note that
	 * initialization can fail if there is a network failure.
	 */
	private void ensureApplicationInitialized() {
		if (applicationInitializing || applicationInitialized) {
			return;
		}

		applicationInitializing = true;

		asyncOnInitialize(new AsyncCallback<Widget>() {
			public void onFailure(Throwable reason) {
				applicationInitializing = false;
				Window.alert("Failed to download code for this widget ("
						+ reason + ")");
			}

			public void onSuccess(Widget result) {
				applicationInitializing = false;
				applicationInitialized = true;

				Widget widget = result;
				if (widget != null) {
					view.setApplication(widget);
				}
				onInitializeComplete();
			}
		});
	}

}
