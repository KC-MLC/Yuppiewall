
package gabriel.yuppiewall.marketdata.repository;

import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;


public interface StockDailySummaryRepository {
	
	void saveStockDailySummary(StockDailySummary_ tradingService);
	
	void saveStockDailySummary(StockDailySummary_[] tradingService);

	void saveStockDailySummary(String csvStockDailySummary);
	
	
}
