package gabriel.yuppiewall.marketdata.service;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.indicator.trend.ExponentialMovingAverage;
import gabriel.yuppiewall.indicator.trend.SimpleMovingAverage;
import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

public class ScannerService implements Scanner {

	@Override
	public void scan() {
		// Hard Coded Business Rule will make them configurable later
		// 1 ) Find all Stock from curent - 150 day
		Exchange_ exchange = new Exchange_("NYSE");
		Date dateFrom = getMarketRepository().getTradingDate(exchange,
				new Date(), 150);
		Expresion expresion = Expresion.between("date", dateFrom, new Date());
		Map<String, StockDailySummary_[]> stockData = getStockDailySummaryRepository()
				.findStock(exchange, expresion);
		Iterator<String> itr = stockData.keySet().iterator();

		while (itr.hasNext()) {
			String symbol = itr.next();
			StockDailySummary_[] historical = stockData.get(symbol);
			// calculate 10 day sma
			TechnicalIndicator_[] ti = new SimpleMovingAverage().calculate(
					historical, 10);

			// from the rule if Min(closing price of last 5 day) > today SMA
			StockDailySummary_ min = FU.findMinimum(historical,
					historical.length - 5, historical.length,
					new Comparator<StockDailySummary_>() {
						@Override
						public int compare(StockDailySummary_ o1,
								StockDailySummary_ o2) {

							return o1.getStockPriceAdjClose().compareTo(
									o2.getStockPriceAdjClose());
						}
					});
			if (min.getStockPriceAdjClose().compareTo(
					ti[ti.length - 1].getValue()) == -1) {
				// remove this stock from consideration
				itr.remove();
				continue;
			}
			// EMA(stockList 5 day) and EMA(stocklist 35 day)
			TechnicalIndicator_[] ema5 = new ExponentialMovingAverage()
					.calculate(historical, 5);
			TechnicalIndicator_[] ema35 = new ExponentialMovingAverage()
					.calculate(historical, 35);

			if (ema5[ema5.length - 1].getValue().compareTo(
					ema35[ema35.length - 1].getValue()) == -1) {
				// remove this stock from consideration
				itr.remove();
				continue;
			} else {
				// SYSO BULL RUN ON THIS STOCK
			}
		}

	}
}
