package gabriel.yuppiewall.indicator.trend;

import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

public class SimpleMovingAverage {
	
	
	void calculate(StockDailySummary_ today, StockDailySummary_ historical[], int days)
	{
		if (historical.length >= days)
		{
			long sum = 0;
			for(int index =0; index <= days;index++){
				sum +=historical[index].getStockPriceLow().longValue();
			}
			sum = sum/days;
		}
	}

}
