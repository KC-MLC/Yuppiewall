package gabriel.yuppiewall.jpa.marketdata.repository;

import gabriel.yuppiewall.common.Tupple;
import gabriel.yuppiewall.common.exception.InvalidParameterValueException;
import gabriel.yuppiewall.common.exception.MissingRequiredFiledException;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.jpa.market.domain.JPAExchange;
import gabriel.yuppiewall.jpa.market.repository.TradeDayRepository;
import gabriel.yuppiewall.jpa.marketdata.domain.JPAEndOfDayData;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.GlobalFilter;
import gabriel.yuppiewall.scanner.domain.ScanParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("JPAEndOfDayDataRepository")
public class JPAEndOfDayDataRepository implements EndOfDayDataRepository {

	@Autowired
	private JPAEODDataRepository jpaEODDataRepository;

	@Autowired
	private TradeDayRepository tradeDayRepository;

	@Override
	public void createEndOfDayData(EndOfDayData endOfDayData) {
		jpaEODDataRepository.saveAndFlush(new JPAEndOfDayData(endOfDayData));
	}

	@Override
	public void createEndOfDayData(List<EndOfDayData> list) {

		List<JPAEndOfDayData> convertedList = new ArrayList<JPAEndOfDayData>(
				list.size());

		for (EndOfDayData endOfDayData_ : list) {
			convertedList.add(new JPAEndOfDayData(endOfDayData_));
			/*
			 * jpaEODDataRepository.save(new JPAEndOfDayData(endOfDayData_)); if
			 * (++counter == 20) { jpaEODDataRepository.flush(); counter = 0; }
			 */
		}
		/*
		 * if (counter != 0) jpaEODDataRepository.flush();
		 */
		jpaEODDataRepository.save(convertedList);
		// jpaEODDataRepository.flush();

	}

	@Override
	public ScanRequest createScanRequest(ScanParameter param) {
		// TODO only supporting two parameter from query should add
		// implementation for average function also
		GlobalFilter gfilter = param.getGlobalFilter();
		if (gfilter == null)
			throw new MissingRequiredFiledException(GlobalFilter.class,
					"globalFilter", "Missing Global Filter");
		Tupple<String, String> group = gfilter.getGroup();
		if (group == null)
			throw new MissingRequiredFiledException(GlobalFilter.class,
					"globalFilter", "Missing Global Filter");
		List<JPAEndOfDayData> list = null;
		String key = group.getKey();
		if ("country".equals(key)) {
			list = jpaEODDataRepository.findAllByCountry(group.getValue());
		} else if ("exchange".equals(key)) {
			list = jpaEODDataRepository.findAllByExchange(new JPAExchange(
					new Exchange(group.getValue())));
		} else {
			throw new InvalidParameterValueException(GlobalFilter.class,
					"globalFilter", key + " Fileter Not supported");
		}
		// group them in symbol
		Map<Instrument, List<EndOfDayData>> groupedValue = new HashMap<Instrument, List<EndOfDayData>>();

		for (JPAEndOfDayData jpaEndOfDayData : list) {
			EndOfDayData eod = jpaEndOfDayData.getEndOfDayData();
			List<EndOfDayData> eodList = groupedValue.get(eod.getInstrument());
			if (eodList == null) {
				eodList = new ArrayList<>();
				groupedValue.put(eod.getInstrument(), eodList);
			}
			eodList.add(eod);
		}
		return new ScanRequest(groupedValue.keySet(), groupedValue);
	}
}
