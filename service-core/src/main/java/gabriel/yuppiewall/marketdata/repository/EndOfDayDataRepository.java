package gabriel.yuppiewall.marketdata.repository;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;

public interface EndOfDayDataRepository {

	void createEndOfDayData(EndOfDayData_ endOfDayData);

	/*
	 * void saveStockDailySummary(StockDailySummary_[] tradingService);
	 * 
	 * void saveStockDailySummary(String csvStockDailySummary);
	 * 
	 * Map<String, StockDailySummary_[]> findStock(Exchange_ exchange, Expresion
	 * expresion);
	 */

}
