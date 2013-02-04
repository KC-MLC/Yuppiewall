package gabriel.yuppiewall.ws.marketdata.repository;

import gabriel.util.rpc.http.HTTPProxy;
import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class WsSystemDataRepository implements SystemDataRepository {

	@Autowired
	@Qualifier("serverUrl")
	private String serverUrl;

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
	public Exchange getExchange(Exchange exchange) {
		HTTPProxy proxy = new HTTPProxy(serverUrl);
		SystemDataRepository systemRepository = (SystemDataRepository) proxy
				.getService(SystemDataRepository.class);
		exchange = systemRepository.getExchange(exchange);
		/*
		 * exchange = template.getForObject(serverUrl + "/systemdata/exchange",
		 * Exchange.class, exchange);
		 */
		return exchange;
	}

	@Override
	public List<Instrument> getManagedInstrument(Server server) {
		/*
		 * RestTemplate template = new RestTemplate(); List<Instrument> list =
		 * template.getForObject(serverUrl + "/systemdata/managedinstrument",
		 * List.class, server); return list;
		 */
		HTTPProxy proxy = new HTTPProxy(serverUrl);
		SystemDataRepository systemRepository = (SystemDataRepository) proxy
				.getService(SystemDataRepository.class);
		return systemRepository.getManagedInstrument(server);
	}

	@Override
	public void setRegionIdentiy(String regionID) {
		throw new UnsupportedOperationException("method not implemented");

	}

	@Override
	public Instrument getInstrument(Instrument inst) {
		throw new UnsupportedOperationException("method not implemented");
	}

	@Override
	public Collection<Instrument> getInstruments() {
		throw new UnsupportedOperationException("method not implemented");
	}

}
