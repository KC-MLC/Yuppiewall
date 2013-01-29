package gabriel.yuppiewall.marketdata.repository;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.util.List;

public interface EndOfDayDataRepository {

	void createEndOfDayData(EndOfDayData endOfDayData);

	void createEndOfDayData(List<EndOfDayData> list);

	List<EndOfDayData> findAllEndOfDayData(Instrument instrument);

}
