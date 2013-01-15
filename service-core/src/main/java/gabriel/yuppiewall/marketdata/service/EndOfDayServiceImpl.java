package gabriel.yuppiewall.marketdata.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.market.service.MarketService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;

public abstract class EndOfDayServiceImpl implements EndOfDayService {

	@Override
	public void saveEOD(EndOfDayData[] endOfDayData_) {
		// group the record based on echange:date
		Map<String/* exchange:date */, List<EndOfDayData>> record = new HashMap<String, List<EndOfDayData>>();
		SimpleDateFormat sdf = new SimpleDateFormat(EndOfDayData.DATE_FORMAT);
		ArrayList<EndOfDayData> listAll = new ArrayList<EndOfDayData>();
		for (EndOfDayData eod : endOfDayData_) {
			// create key
			String key = eod.getExchange().getName() + ":"
					+ sdf.format(eod.getDate());
			List<EndOfDayData> list = record.get(key);
			if (list == null) {
				list = new ArrayList<EndOfDayData>();
				record.put(key, list);
			}
			list.add(eod);
			listAll.add(eod);
		}

		Iterator<String> recordItr = record.keySet().iterator();
		System.out.println("KKKKKKKKKKKKKKKKKKKKK>" + record.size());
		while (recordItr.hasNext()) {
			String key = recordItr.next();
			String[] keydata = key.split(":");
			// TODO check if this step is necessary
			{
				try {
					getMarketService().createIfNotPresent(
							new Exchange(keydata[0]), sdf.parse(keydata[1]));
				} catch (ParseException ignore) {
					ignore.printStackTrace();
				}
			}

			// getEndOfDayDataRepository().createEndOfDayData(record.get(key));
		}
		System.out.println("***************************>" + listAll.size());
		getEndOfDayDataRepository().createEndOfDayData(listAll);
	}

	protected abstract EndOfDayDataRepository getEndOfDayDataRepository();

	protected abstract MarketService getMarketService();

}
