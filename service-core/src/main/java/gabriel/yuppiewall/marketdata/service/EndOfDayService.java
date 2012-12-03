package gabriel.yuppiewall.marketdata.service;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;

public interface EndOfDayService {

	public void saveEOD(EndOfDayData_[] eod);
}
