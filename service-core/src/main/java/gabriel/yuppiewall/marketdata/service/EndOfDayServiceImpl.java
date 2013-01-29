package gabriel.yuppiewall.marketdata.service;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;

import java.util.Arrays;
import java.util.List;

public abstract class EndOfDayServiceImpl implements EndOfDayService {

	@Override
	public void saveEOD(EndOfDayData[] endOfDayData) {
		List<EndOfDayData> listAll = Arrays.asList(endOfDayData);
		getEndOfDayDataRepository().createEndOfDayData(listAll);
	}

	protected abstract EndOfDayDataRepository getEndOfDayDataRepository();
}
