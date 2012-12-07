package gabriel.yuppiewall.marketdata.service;

import gabriel.yuppiewall.common.Expresion;
import gabriel.yuppiewall.common.FU;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.indicator.trend.ExponentialMovingAverage;
import gabriel.yuppiewall.indicator.trend.SimpleMovingAverage;
import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.market.repository.MarketRepository;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public abstract class ScannerService implements Scanner {
	
	/**
	 * Input
	 * 		exchange : "NYSE"
	 * 		range
	 * 			from : now
	 * 			day/past_date : date/day
	 * 
	 * Filter
	 * 		SMA(10)
	 * 
	 *   
	 *   
	 */

	@Override
	public void scan() {
		// Hard Coded Business Rule will make them configurable later
		// 1 ) Find all Stock from current - 150 day
		Exchange_ exchange = new Exchange_("NYSE");
		Date dateFrom = getMarketRepository().getTradingDate(exchange,
				new Date(), 150);
		// Expresion expresion = Expresion.between("date", dateFrom, new
		// Date());
		Map<String, EndOfDayData_[]> stockData = null/*
													 * getEndOfDayDataRepository(
													 * ) .findStock(exchange,
													 * expresion)
													 */;
		Iterator<String> itr = stockData.keySet().iterator();

		while (itr.hasNext()) {
			String symbol = itr.next();
			EndOfDayData_[] historical = stockData.get(symbol);
			// calculate 10 day sma
			TechnicalIndicator_[] ti = new SimpleMovingAverage().calculate(
					historical, 10);

			// from the rule if Min(closing price of last 5 day) > today SMA
			EndOfDayData_ min = FU.findMinimum(historical,
					historical.length - 5, historical.length,
					new Comparator<EndOfDayData_>() {
						@Override
						public int compare(EndOfDayData_ o1, EndOfDayData_ o2) {

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

	protected abstract EndOfDayDataRepository getEndOfDayDataRepository();

	protected abstract MarketRepository getMarketRepository();
}
