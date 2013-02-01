package gabriel.yuppiewall.ws.scanner.service;

import gabriel.util.rpc.http.RPCServlet;
import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.system.service.ServerCacheService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

//@Controller
//@RequestMapping("/cache")
@SuppressWarnings("serial")
@WebServlet("/ServerCacheService")
public class EndOfDayCacheController extends RPCServlet implements
		ServerCacheService {

	@Autowired
	@Qualifier("dataStore")
	private DataStore dataStore;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		WebApplicationContext appContext = WebApplicationContextUtils
				.getWebApplicationContext(servletConfig.getServletContext());
		dataStore = (DataStore) appContext.getBean("dataStore");
	}

	/*
	 * @RequestMapping(value = "/", method = RequestMethod.PUT)
	 * 
	 * @ResponseStatus(HttpStatus.ACCEPTED) public void put(@RequestBody
	 * List<EndOfDayData> eodList) {
	 * 
	 * dataStore.addAll(eodList);
	 * 
	 * }
	 */

	@RequestMapping(value = "/size", method = RequestMethod.GET)
	@Override
	public @ResponseBody
	Integer size() {
		return dataStore.getSize();
	}

	@RequestMapping(value = "/identity", method = RequestMethod.POST)
	@Override
	public @ResponseBody
	Boolean setIdentity(@RequestBody String requestID,
			@RequestBody Server server) {
		System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwGOT requestID>(" + requestID + ") FOR SERVER >"
				+ server);
		if (dataStore.validateRequestID(requestID)) {
			dataStore.setServer(server);
			System.out.println("GOT requestID>(" + requestID + ") FOR SERVER >"
					+ server + ") YES");
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/clear", method = RequestMethod.DELETE)
	@Override
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void clear() {
		dataStore.clear();
	}
}
