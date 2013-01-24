package gabriel.yuppiewall.spring.scanner.service;

import gabriel.yuppiewall.common.Tupple;
import gabriel.yuppiewall.common.exception.InvalidParameterValueException;
import gabriel.yuppiewall.common.exception.MissingRequiredFiledException;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.jdbc.ds.marketdata.repository.JDBCSymbolStore;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.GlobalFilter;
import gabriel.yuppiewall.scanner.domain.ScanParameter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "DSEndOfDayDataRepository")
public class DSEndOfDayDataRepository implements EndOfDayDataRepository {

	@Autowired
	@Qualifier("JDBCSymbolStore")
	private JDBCSymbolStore symbolStore;

	@Override
	public void createEndOfDayData(EndOfDayData endOfDayData) {
		throw new UnsupportedOperationException(
				"not implemented>createEndOfDayData");

	}

	@Override
	public void createEndOfDayData(List<EndOfDayData> list) {
		throw new UnsupportedOperationException(
				"not implemented>createEndOfDayData");
	}

	@Override
	public ScanRequest createScanRequest(ScanParameter param) {
		// TODO access the cache and create a scan Repo
		// JDBCSymbolStore

		GlobalFilter gfilter = param.getGlobalFilter();
		Tupple<String, String> group = gfilter.getGroup();
		if (group == null)
			throw new MissingRequiredFiledException(GlobalFilter.class,
					"globalFilter", "Missing Global Filter");
		final List<Instrument> list;// = new ArrayList<>();
		String key = group.getKey();

		if ("country".equals(key)) {
			list = new ArrayList<Instrument>(symbolStore.getSymbolList());
		} else if ("exchange".equals(key)) {
			list = new ArrayList<Instrument>();
			String value = group.getValue().toLowerCase();

			List<Instrument> cacheList = symbolStore.getSymbolList();
			for (Instrument instrument : cacheList) {
				if (instrument.getExchange().getName().equals(value)) {
					list.add(instrument);
				}
			}
		} else {
			throw new InvalidParameterValueException(GlobalFilter.class,
					"globalFilter", key + " Fileter Not supported");
		}
		ScanRequest req = new ScanRequest(list);
		req.setConditions(param.getConditions());

		return req;
	}
}
