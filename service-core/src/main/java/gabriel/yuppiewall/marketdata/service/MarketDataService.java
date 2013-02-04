package gabriel.yuppiewall.marketdata.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.domain.InstrumentMarketDetail;

import java.util.List;
import java.util.Map;

public interface MarketDataService {

	List<InstrumentMarketDetail> getInstrmentCurrentStatus(
			List<Instrument> instrument);

	Map<String, List<EndOfDayData>> findAllEndOfDayData(
			List<Instrument> instrument, int offset, int start);

}
