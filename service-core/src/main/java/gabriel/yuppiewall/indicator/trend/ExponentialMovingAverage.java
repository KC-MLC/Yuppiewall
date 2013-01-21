package gabriel.yuppiewall.indicator.trend;

import gabriel.yuppiewall.ds.domain.TechnicalIndicator_;
import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.Expression;

import java.math.BigDecimal;
import java.util.List;

public class ExponentialMovingAverage implements TechnicalIndicator {

	// from
	// http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:moving_averages
	// SMA: 10 period sum / 10
	// Multiplier: (2 / (Time periods + 1) ) = (2 / (10 + 1) ) = 0.1818 (18.18%)
	// EMA: {Close - EMA(previous day)} x multiplier + EMA(previous day).
	@Override
	public TechnicalIndicator_[] calculate(List<EndOfDayData> historical,
			Expression exp) {
		EndOfDayDataScanOnValue mapper = EndOfDayDataScanOnValue.getMapper(exp
				.getScanOn());

		int n = Integer.parseInt(exp.getParameters());
		TechnicalIndicator_[] results = new TechnicalIndicator_[historical
				.size() - n];
		int rIndex = 0;
		float k = 2F / (n + 1);
		BigDecimal K = new BigDecimal(k);
		// BigDecimal Km1 = new BigDecimal(1 - k);
		// calculate seed EMAn
		BigDecimal seedEMA = new SimpleMovingAverage().calculate(historical, n,
				n, mapper);
		BigDecimal ema = seedEMA;
		for (int t = n; t < historical.size(); t++) {

			ema = mapper.getValue(historical.get(t)).subtract(ema).multiply(K)
					.add(ema);
			/*
			 * ema = historical[t].getStockPriceAdjClose().multiply(K.add(ema))
			 * .multiply(Km1);
			 */
			results[rIndex++] = new TechnicalIndicator_(historical.get(t)
					.getDate(), "EMA", n + "DAY", ema);
			System.out.println(t + "\t" + historical.get(t).getDate() + "\t"
					+ ema);
		}

		return null;
	}

}
