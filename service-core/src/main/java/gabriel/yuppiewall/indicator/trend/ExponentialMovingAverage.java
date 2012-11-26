package gabriel.yuppiewall.indicator.trend;

import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;

import java.math.BigDecimal;

public class ExponentialMovingAverage implements TechnicalIndicator {

	// from
	// http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:moving_averages
	// SMA: 10 period sum / 10
	// Multiplier: (2 / (Time periods + 1) ) = (2 / (10 + 1) ) = 0.1818 (18.18%)
	// EMA: {Close - EMA(previous day)} x multiplier + EMA(previous day).
	@Override
	public TechnicalIndicator_[] calculate(EndOfDayData_[] historical, int n) {

		TechnicalIndicator_[] results = new TechnicalIndicator_[historical.length
				- n];
		int rIndex = 0;
		float k = 2F / (n + 1);
		BigDecimal K = new BigDecimal(k);
		// BigDecimal Km1 = new BigDecimal(1 - k);
		// calculate seed EMAn
		BigDecimal seedEMA = new SimpleMovingAverage().calculate(historical, n,
				n);
		BigDecimal ema = seedEMA;
		for (int t = n; t < historical.length; t++) {

			ema = historical[t].getStockPriceAdjClose().subtract(ema)
					.multiply(K).add(ema);
			/*
			 * ema = historical[t].getStockPriceAdjClose().multiply(K.add(ema))
			 * .multiply(Km1);
			 */
			results[rIndex++] = new TechnicalIndicator_(
					historical[t].getDate(), "EMA", n + "DAY", ema);
			System.out.println(t + "\t" + historical[t].getDate() + "\t" + ema);
		}

		return null;
	}

}
