package gabriel.yuppiewall.marketdata.repository;

import java.util.List;
import java.util.Map;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.scanner.domain.ScanParameter;

public interface EndOfDayDataRepository {

	void createEndOfDayData(EndOfDayData_ endOfDayData);

	void createEndOfDayData(List<EndOfDayData_> list);

	Map<String, List<EndOfDayData_>> findRecords(ScanParameter param);

	/*
	 * void saveStockDailySummary(StockDailySummary_[] tradingService);
	 * 
	 * void saveStockDailySummary(String csvStockDailySummary);
	 * 
	 * Map<String, StockDailySummary_[]> findStock(Exchange_ exchange, Expresion
	 * expresion);
	 */

}
