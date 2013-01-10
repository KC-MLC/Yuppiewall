package gabriel.yuppiewall.vaadin;

import gabriel.yuppiewall.um.domain.PrimaryPrincipal;
import gabriel.yuppiewall.vaadin.application.ApplicationService;
import gabriel.yuppiewall.vaadin.application.portfolio.PortfolioApplication;
import gabriel.yuppiewall.vaadin.application.scanner.ScannerApplication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
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
@Component("applicationBean")
@Scope("prototype")
public class YuppiewallUI extends Application implements Window.CloseListener,
		Serializable, HttpServletRequestListener {

	private static ThreadLocal<YuppiewallUI> MAIN = new ThreadLocal<YuppiewallUI>();

	public YuppiewallShell uiController;
	private transient HttpSession session;

	@Autowired
	private ApplicationService applicationService;

	@Resource
	private transient ApplicationContext context;

	@Resource
	private transient ApplicationEventPublisher eventPublisher;

	// Application data
	private PrimaryPrincipal user = new PrimaryPrincipal(
			"khushboo.choudhary@gmail.com");

	private Map<String, Serializable> appData = new HashMap<>();

	// @PostConstruct
	@Override
	public void init() {
		/*
		 * final YuppiewallShell shell = new YuppiewallShell(); //
		 * layout.setMargin(true); setContent(shell);
		 */

		{
			/** TODO SUPER HACK **/
			// ac = ApplicationContextProvider.getApplicationContext();
			/*
			 * applicationService = (ApplicationService) ac
			 * .getBean("applicationService"); uiController = (YuppiewallShell)
			 * ac.getBean("yuppiewallShell");
			 */

		}
		{
			/** TODO REMOVE REGISTER MODULE THIS SHOULD BE VIA SPRING **/
			applicationService.registerApplication(new ScannerApplication());
			applicationService.registerApplication(new PortfolioApplication());
			appData.put("user", user);
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
		session.invalidate();
		uiController = null;

	}

	@SuppressWarnings("unchecked")
	public <T> T getService(String service) {
		return (T) context.getBean(service);

	}

	@SuppressWarnings("unchecked")
	public <T> T getApplicationData(String key) {
		return (T) appData.get(key);

	}

	@Override
	public void onRequestStart(HttpServletRequest request,
			HttpServletResponse response) {
		this.session = request.getSession(false);
		// user = (PrimaryPrincipal) session.getAttribute("principal");
		// TODO check for currentUser being null
		setInstance(this);

	}

	@Override
	public void onRequestEnd(HttpServletRequest request,
			HttpServletResponse response) {
		MAIN.remove();

	}

	public ApplicationService getApplicationService() {
		return applicationService;
	}

	@Override
	public void close() {
		// logger.info("Notifying Controller that session is about to end");
		// uiController.sessionEnd();

		uiController.removeAllComponents();
		try {
			session.invalidate();
		} catch (java.lang.IllegalStateException ignore) {

		}
		super.close();
	}
}
