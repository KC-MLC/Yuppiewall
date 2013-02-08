package gabriel.yuppiewall.marketdata.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.domain.InstrumentMarketDetail;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface MarketDataService {

	Collection<InstrumentMarketDetail> getInstrmentCurrentStatus(
			List<Instrument> instrument);

	Map<String, List<EndOfDayData>> findAllEndOfDayData(
			Collection<Instrument> instrument, int offset, int start);

}
