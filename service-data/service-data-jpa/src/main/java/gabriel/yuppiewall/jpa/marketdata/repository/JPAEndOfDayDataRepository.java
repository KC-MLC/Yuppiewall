package gabriel.yuppiewall.jpa.marketdata.repository;

import gabriel.yuppiewall.jpa.market.domain.JPAExchange;
import gabriel.yuppiewall.jpa.market.repository.TradeDayRepository;
import gabriel.yuppiewall.jpa.marketdata.domain.JPAEndOfDayData;
import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.scanner.domain.ScanParameter;

import java.util.ArrayList;
import java.util.Date;
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
	public void createEndOfDayData(EndOfDayData_ endOfDayData) {
		jpaEODDataRepository.saveAndFlush(new JPAEndOfDayData(endOfDayData));
	}

	@Override
	public void createEndOfDayData(List<EndOfDayData_> list) {

		List<JPAEndOfDayData> convertedList = new ArrayList<JPAEndOfDayData>(
				list.size());

		// int counter = 0;
		for (EndOfDayData_ endOfDayData_ : list) {
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
		//jpaEODDataRepository.flush();
		
	}

	@Override
	public Map<String, List<EndOfDayData_>> findRecords(ScanParameter param) {
		// get repository criteria

		Exchange_ exchange = param.getExchange();
		Date fromDate = param.getFromDate();
		Date toDate = param.getToDate();
		List<JPAEndOfDayData> records = null;
		if ((fromDate == null) && (toDate == null))
			records = jpaEODDataRepository.findAll(new JPAExchange(exchange));
		Map<String, List<EndOfDayData_>> returnValue = new HashMap<>();

		if (records == null)
			return returnValue;

		for (JPAEndOfDayData jpaEndOfDayData : records) {
			EndOfDayData_ eod = jpaEndOfDayData.getEndOfDayData();
			List<EndOfDayData_> list = returnValue.get(eod.getStockSymbol());
			if (list == null) {
				list = new ArrayList<>();
				returnValue.put(eod.getStockSymbol(), list);
			}
			list.add(eod);
		}

		return returnValue;
	}
}
