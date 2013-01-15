package gabriel.yuppiewall.marketdata.service;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

public interface EndOfDayService {

	public void saveEOD(EndOfDayData[] eod);
}
