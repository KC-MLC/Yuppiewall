package gabriel.yuppiewall.vaadin;

import gabriel.yuppiewall.jdbc.marketdata.repository.JDBCSystemDataRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings("serial")
public class StartupApplication extends HttpServlet {

	@Override
	public void init(final ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		// / Automatically java script can run here
		System.out.println("***********************");
		System.out.println("*** INITIALIZING DB ***");

		new Thread(new Runnable() {

			@Override
			public void run() {
				WebApplicationContext appContext = WebApplicationContextUtils
						.getWebApplicationContext(servletConfig
								.getServletContext());
				JDBCSystemDataRepository jdbcSymbolStore = (JDBCSystemDataRepository) appContext
						.getBean("SystemDataRepository");
				jdbcSymbolStore.init();
				System.out.println("***********");

			}
		}).start();

	}

}
