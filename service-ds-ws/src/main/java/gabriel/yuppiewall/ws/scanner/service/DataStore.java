package gabriel.yuppiewall.ws.scanner.service;

import gabriel.util.rpc.http.HTTPProxy;
import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("dataStore")
public class DataStore {

	@Autowired
	@Qualifier("serverUrl")
	private String serverUrl;

	public enum STATUS {
		INITIALIZED, IN_PROGRESS, NOT_INITIALIZED
	}

	private static final Comparator<EndOfDayData> comparator = new Comparator<EndOfDayData>() {

		@Override
		public int compare(EndOfDayData o1, EndOfDayData o2) {
			return o2.getDate().compareTo(o1.getDate());
		}
	};

	private Map<String /* symbol */, Instrument> record = new ConcurrentHashMap<>();
	private Map<Exchange, Set<Instrument>> exchangeInstrumentList = new HashMap<>();
	private Map<String, Exchange> exchanges = new HashMap<>();
	private Map<String, Date> exchangeUdateTime = new HashMap<>();
	private Set<String> stockWithError = new HashSet<>();
	private Server server;

	private STATUS status = STATUS.NOT_INITIALIZED;

	private String requestID;

	private Date creationTime;

	public Integer getSize() {
		return record.size();
	}

	public void clear() {
		record.clear();
		exchangeInstrumentList.clear();
		exchanges.clear();
		exchangeUdateTime.clear();
		stockWithError.clear();

	}

	public List<EndOfDayData> get(Instrument instrument) {
		return record.get(instrument.getSymbol()).getEodList();

	}

	public void setInstrument(Instrument instrument) {
		List<EndOfDayData> list = instrument.getEodList();
		Collections.sort(list, comparator);
		record.put(instrument.getSymbol(), instrument);
		Set<Instrument> instList = exchangeInstrumentList.get(instrument
				.getExchange());
		if (instList == null) {
			instList = new HashSet<>();
			exchangeInstrumentList.put(instrument.getExchange(), instList);
		}
		instList.add(instrument);
		exchangeUdateTime.put(instrument.getExchange().getSymbol(), new Date());
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {

		if (status == STATUS.INITIALIZED)
			creationTime = new Date();
		this.status = status;

	}

	public LinkedList<Instrument> getInstrumentFromExchange(
			Set<String> exchanges) {
		LinkedList<Instrument> list = new LinkedList<>();

		for (String symbol : exchanges) {
			Set<Instrument> inst = exchangeInstrumentList.get(new Exchange(
					symbol));
			if (inst != null)
				list.addAll(inst);
		}
		return list;

	}

	public Iterator<Instrument> getInstrumentFromExchange(Exchange exchange) {

		return exchangeInstrumentList.get(exchange).iterator();

	}

	public Exchange getExchange(Exchange exchange) {

		return exchanges.get(exchange.getSymbol());
	}

	public void setExchange(Exchange exchange) {
		exchanges.put(exchange.getSymbol(), exchange);
		exchangeUdateTime.put(exchange.getSymbol(), new Date());
	}

	public Server getServer() {
		if (server != null)
			return server;
		// make a server call and get server to return me an id

		String requestID = createRequestID();
		System.out.println("Sending request id>" + requestID);
		/*
		 * RestTemplate template = new RestTemplate();
		 * template.put(serverUrl+"/systemdata/regionIdentiy", requestID);
		 */
		HTTPProxy proxy = new HTTPProxy(serverUrl);
		SystemDataRepository systemRepository = (SystemDataRepository) proxy
				.getService(SystemDataRepository.class);
		systemRepository.setRegionIdentiy(requestID);
		// wait till server has responded
		while (server == null)
			;

		return server;
	}

	public Map<String, Exchange> getExchanges() {
		return exchanges;
	}

	private String createRequestID() {
		return (requestID = UUID.randomUUID().toString());

	}

	public void setServer(Server server) {
		this.server = server;
	}

	public boolean validateRequestID(String requestID) {
		return (this.requestID.equals(requestID));
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public Map<String, Date> getExchangeUpdateTime() {
		return exchangeUdateTime;
	}

	public void addStockWithError(String symbol) {
		stockWithError.add(symbol);
	}

	public Set<String> getStockWithError() {
		return stockWithError;
	}

}
