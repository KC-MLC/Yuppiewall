package gabriel.yuppiewall.indicator.momentum;

import java.math.BigDecimal;
import java.math.RoundingMode;

import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

public class RSI implements TechnicalIndicator {

	/**
	 * RSI = 100 - 100 /(1 + RS)
	 * 
	 * RS = Average Gain / Average Loss
	 * 
	 * Average Gain = [(previous Average Gain) x n-1 + current Gain] / n.
	 * Average Loss = [(previous Average Loss) x n-1 + current Loss] / n.
	 * 
	 * http://stockcharts.com/school/doku.php?id=chart_school:
	 * technical_indicators:relative_strength_in
	 **/

	@Override
	public TechnicalIndicator_[] calculate(StockDailySummary_[] historical,
			int n) {

		// first calculate
		// First Average Gain = Sum of Gains over the past n periods / n.
		// First Average Loss = Sum of Losses over the past n periods / n
		BigDecimal N = new BigDecimal(n);
		BigDecimal Nm1 = new BigDecimal(n - 1);
		BigDecimal sumOfGain = new BigDecimal(0L);
		BigDecimal sumOfLoss = new BigDecimal(0L);
		BigDecimal h100 = new BigDecimal(100);
		BigDecimal u1 = new BigDecimal(1);

		TechnicalIndicator_[] results = new TechnicalIndicator_[historical.length
				- n];
		int rIndex = 0;

		for (int i = 1; i < n; i++) {
			int compareTo = historical[i - 1].getStockPriceAdjClose()
					.compareTo(historical[i].getStockPriceAdjClose());
			if (compareTo == 1)
				sumOfLoss = sumOfLoss.add(historical[i - 1]
						.getStockPriceAdjClose().subtract(
								historical[i].getStockPriceAdjClose()));
			else if (compareTo == -1)
				sumOfGain = sumOfGain.add(historical[i].getStockPriceAdjClose()
						.subtract(historical[i - 1].getStockPriceAdjClose()));

		}
		BigDecimal aveGain = sumOfGain.divide(N, RoundingMode.HALF_UP);
		BigDecimal aveLoss = sumOfLoss.divide(N, RoundingMode.HALF_UP);
		for (int i = n; i < historical.length; i++) {

			int compareTo = historical[i - 1].getStockPriceAdjClose()
					.compareTo(historical[i].getStockPriceAdjClose());
			if (compareTo == 1) {
				aveLoss = aveLoss
						.multiply(Nm1)
						.add(historical[i - 1]
								.getStockPriceAdjClose()
								.subtract(historical[i].getStockPriceAdjClose()))
						.divide(N, RoundingMode.HALF_UP);

				aveGain = aveGain.multiply(Nm1).divide(N, RoundingMode.HALF_UP);

			} else if (compareTo == -1) {
				aveGain = aveGain
						.multiply(Nm1)
						.add(historical[i].getStockPriceAdjClose().subtract(
								historical[i - 1].getStockPriceAdjClose()))
						.divide(N, RoundingMode.HALF_UP);

				aveLoss = aveLoss.multiply(Nm1).divide(N, RoundingMode.HALF_UP);
			} else {
				aveLoss = aveLoss.multiply(Nm1).divide(N, RoundingMode.HALF_UP);
				aveGain = aveGain.multiply(Nm1).divide(N, RoundingMode.HALF_UP);
			}

			BigDecimal rsi = h100.subtract(h100.divide(
					u1.add(aveGain.divide(aveLoss, RoundingMode.HALF_UP)),
					RoundingMode.HALF_UP));

			results[rIndex++] = new TechnicalIndicator_(
					historical[i].getDate(), "RSI", n + "DAY", rsi);

			System.out.println(historical[i].getDate() + "," + rsi);
		}

		return results;
	}
}
