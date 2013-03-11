package gabriel.yuppiewall.gwt.client.application;

import gabriel.yuppiewall.gwt.client.Yuppiewall;
import gabriel.yuppiewall.gwt.client.application.home.HomepageApplication;
import gabriel.yuppiewall.gwt.client.application.portfolio.PortfolioApplication;
import gabriel.yuppiewall.gwt.client.application.scanner.ScannerApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.prefetch.RunAsyncCode;

public class ApplicationRegistry {
	/**
	 * A mapping of {@link ContentWidget}s to their associated categories.
	 */
	private final Map<ApplicationWidget, ApplicationHolder> contentCategory = new HashMap<ApplicationWidget, ApplicationHolder>();

	/**
	 * A mapping of history tokens to their associated {@link ContentWidget}.
	 */
	private final Map<String, ApplicationWidget> contentToken = new HashMap<String, ApplicationWidget>();

	private ArrayList<ApplicationHolder> catList = new ArrayList<ApplicationHolder>();

	/**
	 * A top level category in the tree.
	 */
	public class ApplicationHolder {

		private ApplicationWidget applicationWidget;
		private String name;
		private RunAsyncCode splitPoint;

		public ApplicationHolder(String name) {
			this.name = name;
		}

		public void addApplicationWidget(ApplicationWidget applicationWidget,
				RunAsyncCode splitPoint) {
			this.applicationWidget = applicationWidget;
			this.splitPoint = splitPoint;

			contentCategory.put(applicationWidget, this);
			contentToken.put(
					Yuppiewall.getApplicationWidgetToken(applicationWidget),
					applicationWidget);
		}

		public String getName() {
			return name;
		}

		public ApplicationWidget getApplicationWidget() {
			return applicationWidget;
		}

		/**
		 * Get the list of split points to prefetch for this category.
		 * 
		 * @return the list of classes in this category
		 */
		public RunAsyncCode getSplitPoint() {
			return splitPoint;
		}
	}

	public ApplicationRegistry() {
		initialize();
	}

	/**
	 * Get the content widget associated with the specified history token.
	 * 
	 * @param token
	 *            the history token
	 * @return the associated {@link ContentWidget}
	 */
	public ApplicationWidget getApplicationWidgetForToken(String token) {
		return contentToken.get(token);
	}

	/**
	 * Initialize the top level categories in the tree.
	 */
	private void initialize() {

		HomepageApplication homePage = new HomepageApplication();
		ApplicationHolder app = new ApplicationHolder(homePage.getName());
		catList.add(app);
		// CwCheckBox is the default example, so don't prefetch it.
		app.addApplicationWidget(homePage, null);

		PortfolioApplication portfolio = new PortfolioApplication();
		app = new ApplicationHolder(portfolio.getName());
		catList.add(app);
		app.addApplicationWidget(portfolio,
				RunAsyncCode.runAsyncCode(PortfolioApplication.class));

		ScannerApplication scanner = new ScannerApplication();
		app = new ApplicationHolder(scanner.getName());
		catList.add(app);
		app.addApplicationWidget(scanner,
				RunAsyncCode.runAsyncCode(ScannerApplication.class));

	}

	public List<ApplicationHolder> getAllApplicationWidgets() {

		return catList;
	}
}
