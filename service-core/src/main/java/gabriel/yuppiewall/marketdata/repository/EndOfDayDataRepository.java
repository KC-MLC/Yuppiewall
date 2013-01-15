package gabriel.yuppiewall.marketdata.repository;

import java.util.List;
import java.util.Map;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.ScanParameter;

public interface EndOfDayDataRepository {

	void createEndOfDayData(EndOfDayData endOfDayData);

	void createEndOfDayData(List<EndOfDayData> list);

	Map<String, List<EndOfDayData>> findRecords(ScanParameter param);

	/*
	 * void saveStockDailySummary(StockDailySummary_[] tradingService);
	 * 
	 * void saveStockDailySummary(String csvStockDailySummary);
	 * 
	 * Map<String, StockDailySummary_[]> findStock(Exchange_ exchange, Expresion
	 * expresion);
	 */

}
