package gabriel.yuppiewall.marketdata.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.domain.InstrumentMarketDetail;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class MarketDataServiceImpl implements MarketDataService {

	@Override
	public List<InstrumentMarketDetail> getInstrmentCurrentStatus(
			List<Instrument> instrument) {
		List<InstrumentMarketDetail> retList = new ArrayList<>();
		Map<String, List<EndOfDayData>> eodList = findAllEndOfDayData(
				instrument, 1, 0);
		if (eodList == null)
			return retList;
		Iterator<String> itr = eodList.keySet().iterator();

		while (itr.hasNext()) {
			String symbol = itr.next();
			List<EndOfDayData> eods = eodList.get(symbol);
			retList.add(new InstrumentMarketDetail(eods.get(0).getInstrument(),
					eods.get(0).getStockPriceAdjClose().longValue()));
		}
		return retList;
	}

	@Override
	public Map<String, List<EndOfDayData>> findAllEndOfDayData(
			Collection<Instrument> instrument, int offset, int start) {
		return getEndOfDayDataRepository().findAllEndOfDayData(instrument,
				offset, start);
	}

	protected abstract EndOfDayDataRepository getEndOfDayDataRepository();
}
