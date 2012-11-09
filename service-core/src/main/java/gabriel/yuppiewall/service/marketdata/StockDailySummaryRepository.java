
package gabriel.yuppiewall.service.marketdata;

import gabriel.yuppiewall.domain.marketdata.StockDailySummary_;


public interface StockDailySummaryRepository {
	
	void saveStockDailySummary(StockDailySummary_ tradingService);
	
	void saveStockDailySummary(StockDailySummary_[] tradingService);

	void saveStockDailySummary(String csvStockDailySummary);
	
	
}
