package gabriel.yuppiewall.vaadin;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

@SuppressWarnings("serial")
public class ApplicationServlet extends AbstractApplicationServlet {
	private WebApplicationContext appContext;

	@Override
	protected Application getNewApplication(
			HttpServletRequest httpServletRequest) throws ServletException {

		httpServletRequest.setAttribute("NEW_APP", "NEW_APP");
		Application mMa = (Application) appContext.getBean("applicationBean");
		return mMa;
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		appContext = WebApplicationContextUtils
				.getWebApplicationContext(servletConfig.getServletContext());
	}

	@Override
	protected Class<? extends Application> getApplicationClass()
			throws ClassNotFoundException {
		return YuppiewallUI.class;
	}

}
