package gabriel.yuppiewall.indicator.trend;

import java.math.BigDecimal;

import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

public class ExponentialMovingAverage implements TechnicalIndicator {

	// from
	// http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:moving_averages
	// SMA: 10 period sum / 10
	// Multiplier: (2 / (Time periods + 1) ) = (2 / (10 + 1) ) = 0.1818 (18.18%)
	// EMA: {Close - EMA(previous day)} x multiplier + EMA(previous day).
	@Override
	public BigDecimal calculate(StockDailySummary_[] historical, int n) {

		float k = 2F / (n + 1);
		BigDecimal K = new BigDecimal(k);
		// BigDecimal Km1 = new BigDecimal(1 - k);
		// calculate seed EMAn
		BigDecimal seedEMA = new SimpleMovingAverage().calculate(historical, n,
				n);
		System.out.println(seedEMA);
		BigDecimal ema = seedEMA;
		for (int t = n; t < historical.length; t++) {

			ema = historical[t].getStockPriceAdjClose().subtract(ema)
					.multiply(K).add(ema);
			/*
			 * ema = historical[t].getStockPriceAdjClose().multiply(K.add(ema))
			 * .multiply(Km1);
			 */
			System.out.println(t + "\t" + historical[t].getDate() + "\t" + ema);
		}

		return ema;
	}

}
