package gabriel.yuppiewall.indicator.trend;

import java.math.BigDecimal;

import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

public class ExponentialMovingAverage implements TechnicalIndicator {

	// EMA = Price(t) * k + EMA(y) * (1 – k)
	// t = today, y = yesterday, N = number of days in EMA, k = 2/(N+1)
	@Override
	public BigDecimal calculate(StockDailySummary_[] historical, int n) {

		float k = 2F / (n + 1);
		BigDecimal K = new BigDecimal(k);
		BigDecimal Km1 = new BigDecimal(1 - k);
		// calculate seed EMA
		BigDecimal seedEMA = new SimpleMovingAverage().calculate(historical, n,
				n);
		BigDecimal ema = seedEMA;
		for (int t = n; t < historical.length; t++) {
			ema = historical[t].getStockPriceAdjClose().multiply(K.add(ema))
					.multiply(Km1);
		}

		return ema;
	}
}
