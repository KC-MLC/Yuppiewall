package gabriel.yuppiewall.vaadin;

import gabriel.util.rpc.http.HTTPProxy;
import gabriel.util.rpc.http.RPCServlet;
import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.system.service.ServerCacheService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

//@Controller("restSystemDataRepository")
//@RequestMapping("/systemdata")
@SuppressWarnings("serial")
@WebServlet("/SystemDataRepository")
public class RestSystemDataRepository extends RPCServlet implements
		SystemDataRepository {

	public RestSystemDataRepository() {
		System.out
				.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHLLLO");
	}

	@Autowired
	@Qualifier("JDBCSystemDataRepository")
	private SystemDataRepository systemDataRepository;
	private WebApplicationContext appContext;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		appContext = WebApplicationContextUtils
				.getWebApplicationContext(servletConfig.getServletContext());
		systemDataRepository = (SystemDataRepository) appContext
				.getBean("JDBCSystemDataRepository");
	}

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public ResponseEntity ping() {

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@Override
	@RequestMapping(value = "/exchange", method = RequestMethod.GET)
	public @ResponseBody
	Exchange getExchange(@RequestBody Exchange exchange) {
		return systemDataRepository.getExchange(exchange);
	}

	@Override
	@RequestMapping(value = "/managedinstrument", method = RequestMethod.GET)
	public @ResponseBody
	List<Instrument> getManagedInstrument(@RequestBody Server server) {
		return systemDataRepository.getManagedInstrument(server);
	}

	@Override
	@RequestMapping(value = "/regionidentiy", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void setRegionIdentiy(@RequestBody String regionID) {
		// make server call to all of the region server and pass
		// RestTemplate template = new RestTemplate();
		System.out.println("GOT REQUEST ID>" + regionID);
		Collection<Server> serverList = systemDataRepository.getServerList();
		for (Server server : serverList) {
			try {
				String url = server.getServerContext().replaceAll("/api", "");
				System.out.println("Clling server>" + url);
				HTTPProxy proxy = new HTTPProxy(url);
				ServerCacheService cacheService = (ServerCacheService) proxy
						.getService(ServerCacheService.class);
				Boolean v = cacheService.setIdentity(regionID, server);
				System.out.println("Clling server Resonded>" + url + ") " + v);
				/*
				 * Boolean v = template.postForObject(server.getServerContext()
				 * + "/cache/identity", new Tupple(regionID, server),
				 * Boolean.class);
				 */

				if (v)
					break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<Exchange> getExchangeByCountryCode(String value) {
		throw new UnsupportedOperationException("method not implemented");

	}

	@Override
	public Collection<Server> getServerList() {
		throw new UnsupportedOperationException("method not implemented");

	}

	@Override
	public Set<Server> getExchangeServerList(Exchange exchange) {
		throw new UnsupportedOperationException("method not implemented");

	}

	@Override
	public Exchange getExchange(Instrument instrument) {
		throw new UnsupportedOperationException("method not implemented");

	}

	@Override
	public LinkedList<Instrument> getInstrumentFromExchange(
			Set<String> exchanges) {
		throw new UnsupportedOperationException("method not implemented");

	}

	@Override
	public Instrument getInstrument(Instrument inst) {
		throw new UnsupportedOperationException("method not implemented");
	}
}
