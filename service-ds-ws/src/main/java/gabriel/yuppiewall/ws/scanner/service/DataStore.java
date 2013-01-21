package gabriel.yuppiewall.ws.scanner.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore {

	private static final Comparator<EndOfDayData> comparator = new Comparator<EndOfDayData>() {

		@Override
		public int compare(EndOfDayData o1, EndOfDayData o2) {
			return o2.getDate().compareTo(o1.getDate());
		}
	};

	private static Map<Instrument, List<EndOfDayData>> groupedValue = new ConcurrentHashMap<>();

	private static void put(EndOfDayData data) {
		Instrument instrument = data.getInstrument();
		List<EndOfDayData> value = groupedValue.get(instrument);

		if (value == null) {
			value = new ArrayList<EndOfDayData>();
			groupedValue.put(instrument, value);
		}
		value.add(data);
	}

	public static void addAll(List<EndOfDayData> eodList) {
		Set<Instrument> listtosort = new HashSet<>();
		for (EndOfDayData eod : eodList) {
			put(eod);
			listtosort.add(eod.getInstrument());
		}
		for (Instrument instrument : listtosort) {
			List<EndOfDayData> list = groupedValue.get(instrument);
			Collections.sort(list, comparator);
		}
	}

	public static Integer getSize() {
		return groupedValue.size();
	}

	public static void clear() {
		groupedValue.clear();
	}

	public static List<EndOfDayData> get(Instrument instrument) {
		return groupedValue.get(instrument);
	}

	public static Iterator<Instrument> keySetIterator() {
		return new LinkedList<Instrument>(groupedValue.keySet()).iterator();
	}

}
