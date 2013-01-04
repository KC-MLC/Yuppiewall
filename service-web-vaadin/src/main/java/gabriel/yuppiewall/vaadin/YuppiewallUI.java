package gabriel.yuppiewall.vaadin;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdsi.bmrc.client.application.ApplicationService;
import com.sdsi.bmrc.client.application.ApplicationServiceImpl;
import com.sdsi.bmrc.client.application.scanner.PortfolioApplication;
import com.sdsi.bmrc.client.application.scanner.ScannerApplication;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

/**
 * The Application's "main" class
 */
public class YuppiewallUI extends Application implements Window.CloseListener,
		Serializable, HttpServletRequestListener {

	private static ThreadLocal<YuppiewallUI> MAIN = new ThreadLocal<YuppiewallUI>();
	private YuppiewallShell uiController;

	// @Autowired
	private ApplicationService applicationService = new ApplicationServiceImpl();

	@Override
	public void init() {
		/*
		 * final YuppiewallShell shell = new YuppiewallShell(); //
		 * layout.setMargin(true); setContent(shell);
		 */

		{
			/** TODO REMOVE REGISTER MODULE THIS SHOULD BE VIA SPRING **/
			applicationService.registerApplication(new ScannerApplication());
			applicationService.registerApplication(new PortfolioApplication());
		}

		setInstance(this);
		uiController = new YuppiewallShell();

		setMainWindow(uiController);

	}

	public static YuppiewallUI getInstance() {
		return MAIN.get();
	}

	// Set the current application instance
	public static void setInstance(YuppiewallUI application) {
		if (getInstance() == null) {
			MAIN.set(application);
		}
	}

	@Override
	public void windowClose(CloseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRequestStart(HttpServletRequest request,
			HttpServletResponse response) {
		setInstance(this);

	}

	@Override
	public void onRequestEnd(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	public ApplicationService getApplicationService() {
		return applicationService;
	}
}
