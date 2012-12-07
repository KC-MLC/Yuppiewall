package gabriel.yuppiewall.marketdata.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.market.service.MarketService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;

public abstract class EndOfDayServiceImpl implements EndOfDayService {

	@Override
	public void saveEOD(EndOfDayData_[] endOfDayData_) {
		// group the record based on echange:date
		Map<String/* exchange:date */, List<EndOfDayData_>> record = new HashMap<String, List<EndOfDayData_>>();
		SimpleDateFormat sdf = new SimpleDateFormat(EndOfDayData_.DATE_FORMAT);
		for (EndOfDayData_ eod : endOfDayData_) {
			// create key
			String key = eod.getExchange().getName() + ":"
					+ sdf.format(eod.getDate());
			List<EndOfDayData_> list = record.get(key);
			if (list == null) {
				list = new ArrayList<EndOfDayData_>();
				record.put(key, list);
			}
			list.add(eod);
		}

		Iterator<String> recordItr = record.keySet().iterator();
		while (recordItr.hasNext()) {
			String key = recordItr.next();
			String[] keydata = key.split(":");
			// TODO check if this step is necessary
			{
				try {
					getMarketService().createIfNotPresent(
							new Exchange_(keydata[0]), sdf.parse(keydata[1]));
				} catch (ParseException ignore) {
					ignore.printStackTrace();
				}
			}

			getEndOfDayDataRepository().createEndOfDayData(record.get(key));
		}

	}

	protected abstract EndOfDayDataRepository getEndOfDayDataRepository();

	protected abstract MarketService getMarketService();

}
