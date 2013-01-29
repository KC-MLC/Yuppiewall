package gabriel.yuppiewall.ws.scanner.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service("dataStore")
public class DataStore {

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
	private Map<String /* symbol */, Exchange> exchanges = new HashMap<>();
	private STATUS status = STATUS.NOT_INITIALIZED;

	public Integer getSize() {
		return record.size();
	}

	public void clear() {
		record.clear();
	}

	public List<EndOfDayData> get(Instrument instrument) {
		return record.get(instrument.getSymbol()).getEodList();

	}

	public Collection<String> keySetIterator(Set<Exchange> exchanges) {
		Iterator<Instrument> itr = record.values().iterator();
		LinkedList<String> retvalue = new LinkedList<>();
		while (itr.hasNext()) {
			Instrument inst = itr.next();
			if (exchanges.contains(inst.getExchange())) {
				retvalue.add(inst.getSymbol());
			}
		}
		return retvalue;
	}

	public void setInstrument(Instrument instrument) {
		List<EndOfDayData> list = instrument.getEodList();
		Collections.sort(list, comparator);
		record.put(instrument.getSymbol(), instrument);

	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;

	}

	public Exchange getExchange(Exchange exchange) {
		return exchanges.get(exchange.getName());
	}

	public void setExchange(Exchange exchange) {
		exchanges.put(exchange.getName(), exchange);

	}
}
