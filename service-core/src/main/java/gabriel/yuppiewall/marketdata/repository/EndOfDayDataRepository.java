package gabriel.yuppiewall.marketdata.repository;

import gabriel.yuppiewall.common.util.Filter;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface EndOfDayDataRepository {

	void createEndOfDayData(EndOfDayData endOfDayData);

	void createEndOfDayData(List<EndOfDayData> list);

	List<EndOfDayData> findAllEndOfDayData(Instrument instrument,
			Filter<EndOfDayData> filter);

	Map<String, List<EndOfDayData>> findAllEndOfDayData(
			Collection<Instrument> instruments, int offset, int start);

}
