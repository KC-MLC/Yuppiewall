package gabriel.yuppiewall.vaadin;

import gabriel.yuppiewall.vaadin.application.ApplicationService;
import gabriel.yuppiewall.vaadin.application.portfolio.PortfolioApplication;
import gabriel.yuppiewall.vaadin.application.scanner.ScannerApplication;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class YuppiewallUI extends Application implements Window.CloseListener,
		Serializable, HttpServletRequestListener {

	private static ThreadLocal<YuppiewallUI> MAIN = new ThreadLocal<YuppiewallUI>();

	@Autowired
	public YuppiewallShell uiController;

	@Autowired
	private ApplicationService applicationService;

	//@PostConstruct
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
