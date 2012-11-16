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
		// calculate seed EMA
		new SimpleMovingAverage().calculate(historical, n, n);

		return null;
	}
}
