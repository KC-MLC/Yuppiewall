package gabriel.yuppiewall.ws;

import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.ws.scanner.service.DataStore;
import gabriel.yuppiewall.ws.scanner.service.DataStore.STATUS;
import gabriel.yuppiewall.ws.scanner.service.RegionServerInitilizer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class MoniterServlet
 */
// @WebServlet("/MoniterServlet")
public class MoniterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MoniterServlet() {
		super();

	}

	@Autowired
	@Qualifier("dataStore")
	private DataStore dataStore;

	@Autowired
	private RegionServerInitilizer regionServerInitilizer;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		WebApplicationContext appContext = WebApplicationContextUtils
				.getWebApplicationContext(servletConfig.getServletContext());
		dataStore = (DataStore) appContext.getBean("dataStore");
		regionServerInitilizer = (RegionServerInitilizer) appContext
				.getBean("regionServerInitilizer");
	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		dataStore.setStatus(STATUS.NOT_INITIALIZED);
		regionServerInitilizer.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		try {
			out.println("<!DOCTYPE html>");
			out.println("<html><head>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			out.println("<title>Hello, World</title></head>");
			out.println("<body>");
			out.println("<h1>Hello, world!</h1>");
			out.println("<p>Size: " + dataStore.getSize() + "</p>");
			out.println("<p>Status: " + dataStore.getStatus().name() + "</p>");
			String exchange = createStringOfKey(dataStore.getExchanges()
					.keySet().iterator());

			out.println("<p>Managing following Exchange: " + exchange + "</p>");
			String creationTime = (null == dataStore.getCreationTime()) ? "not created"
					: dataStore.getCreationTime().toString();
			out.println("<p>Created on: " + creationTime + "</p>");
			out.println("<p>Updated Exchange on: "
					+ createStringOfExchange(dataStore.getExchangeUpdateTime())
					+ "</p>");

			out.println("<p>Stock having currupt data: "
					+ createStringOfKey(dataStore.getStockWithError()
							.iterator()) + "</p>");

			// Generate a random number upon each request
			out.println("<p>Current Time: <strong>" + new Date()
					+ "</strong></p>");
			out.println("</body>");
			out.println("</html>");
		} finally {
			out.close(); // Always close the output writer
		}
	}

	private String createStringOfExchange(Map<String, Date> exchangeUpdateTime) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> itr = exchangeUpdateTime.keySet().iterator();
		while (itr.hasNext()) {
			String exchange = itr.next();
			Date up = exchangeUpdateTime.get(exchange);
			sb.append(exchange + ":" + up.toString() + " ");

		}
		return sb.toString();
	}

	private String createStringOfKey(Iterator iterator) {
		StringBuilder sb = new StringBuilder();
		while (iterator.hasNext())
			sb.append(iterator.next().toString() + ",");
		return sb.toString();
	}

}
