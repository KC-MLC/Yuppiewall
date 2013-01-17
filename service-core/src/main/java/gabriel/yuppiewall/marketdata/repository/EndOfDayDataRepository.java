package gabriel.yuppiewall.marketdata.repository;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.ScanParameter;

import java.util.List;

public interface EndOfDayDataRepository {

	void createEndOfDayData(EndOfDayData endOfDayData);

	void createEndOfDayData(List<EndOfDayData> list);

	ScanResult findRecords(ScanParameter param);

	/*
	 * void saveStockDailySummary(StockDailySummary_[] tradingService);
	 * 
	 * void saveStockDailySummary(String csvStockDailySummary);
	 * 
	 * Map<String, StockDailySummary_[]> findStock(Exchange_ exchange, Expresion
	 * expresion);
	 */

}
