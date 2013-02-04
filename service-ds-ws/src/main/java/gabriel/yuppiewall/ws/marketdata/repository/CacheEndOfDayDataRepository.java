package gabriel.yuppiewall.ws.marketdata.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import gabriel.yuppiewall.common.util.Filter;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.ws.scanner.service.DataStore;

@Service("CacheEndOfDayDataRepository")
public class CacheEndOfDayDataRepository implements EndOfDayDataRepository {

	@Autowired
	@Qualifier("dataStore")
	private DataStore dataStore;

	@Override
	public void createEndOfDayData(EndOfDayData endOfDayData) {
		throw new UnsupportedOperationException("method not implemented");

	}

	@Override
	public void createEndOfDayData(List<EndOfDayData> list) {
		throw new UnsupportedOperationException("method not implemented");

	}

	@Override
	public List<EndOfDayData> findAllEndOfDayData(Instrument instrument,
			Filter<EndOfDayData> filter) {
		throw new UnsupportedOperationException("method not implemented");

	}

	@Override
	public Map<String, List<EndOfDayData>> findAllEndOfDayData(
			List<Instrument> instruments, int offset, int start) {
		Map<String, List<EndOfDayData>> retValue = new HashMap<String, List<EndOfDayData>>();
		for (Instrument instrument : instruments) {
			List<EndOfDayData> list = dataStore.get(instrument).subList(start,
					offset);
			retValue.put(instrument.getSymbol(), list);
		}

		return retValue;
	}
}
