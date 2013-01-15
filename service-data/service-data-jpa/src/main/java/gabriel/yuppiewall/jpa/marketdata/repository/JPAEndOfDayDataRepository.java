package gabriel.yuppiewall.jpa.marketdata.repository;

import gabriel.yuppiewall.common.Tupple;
import gabriel.yuppiewall.common.exception.InvalidParameterValueException;
import gabriel.yuppiewall.common.exception.MissingRequiredFiledException;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.indicator.trend.SimpleMovingAverage;
import gabriel.yuppiewall.jpa.market.domain.JPAExchange;
import gabriel.yuppiewall.jpa.market.repository.TradeDayRepository;
import gabriel.yuppiewall.jpa.marketdata.domain.JPAEndOfDayData;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.GlobalFilter;
import gabriel.yuppiewall.scanner.domain.ScanParameter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	public Map<String, List<EndOfDayData>> findRecords(ScanParameter param) {
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
		Map<String, List<EndOfDayData>> groupedValue = new HashMap<String, List<EndOfDayData>>();

		for (JPAEndOfDayData jpaEndOfDayData : list) {
			EndOfDayData eod = jpaEndOfDayData.getEndOfDayData();
			List<EndOfDayData> eodList = groupedValue.get(eod.getStockSymbol());
			if (eodList == null) {
				eodList = new ArrayList<>();
				groupedValue.put(eod.getStockSymbol(), eodList);
			}
			eodList.add(eod);
		}

		Condition avePriceConition = gfilter.getAvgPrice();
		if (avePriceConition != null) {
			filter(avePriceConition, groupedValue);

		}
		Condition aveVolConition = gfilter.getAvgVolue();
		if (aveVolConition != null) {
			filter(aveVolConition, groupedValue);
		}
		return groupedValue;
	}

	private static void filter(Condition aveVolConition,
			Map<String, List<EndOfDayData>> groupedValue) {

		if (aveVolConition != null) {
			SimpleMovingAverage sma = new SimpleMovingAverage();
			Iterator<String> itr = groupedValue.keySet().iterator();
			while (itr.hasNext()) {
				String symbol = itr.next();
				List<EndOfDayData> tempList = groupedValue.get(symbol);
				TechnicalIndicator_[] res = sma.calculate(tempList,
						aveVolConition.getLhs());
				if (0 >= res[0].getValue()
						.compareTo(
								new BigDecimal(aveVolConition.getRhs()
										.getParameters()))) {
					itr.remove();
				}
			}
		}
	}

}
