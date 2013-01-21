package gabriel.yuppiewall.indicator.momentum;

import gabriel.yuppiewall.common.FU;
import gabriel.yuppiewall.ds.domain.TechnicalIndicator_;
import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.Expression;

import java.math.BigDecimal;
import java.util.List;

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
	public TechnicalIndicator_[] calculate(List<EndOfDayData> historical,
			Expression exp) {
		EndOfDayDataScanOnValue mapper = EndOfDayDataScanOnValue.getMapper(exp
				.getScanOn());

		// first calculate
		// First Average Gain = Sum of Gains over the past n periods / n.
		// First Average Loss = Sum of Losses over the past n periods / n
		int n = Integer.parseInt(exp.getParameters());
		BigDecimal N = new BigDecimal(n);
		BigDecimal Nm1 = new BigDecimal(n - 1);
		BigDecimal sumOfGain = FU.U0;
		BigDecimal sumOfLoss = FU.U0;

		TechnicalIndicator_[] results = new TechnicalIndicator_[historical
				.size() - n];
		int rIndex = 0;

		for (int i = 1; i < n; i++) {

			int compareTo = mapper.getValue(historical.get(i - 1)).compareTo(
					mapper.getValue(historical.get(i)));
			if (compareTo == 1)
				sumOfLoss = sumOfLoss.add(mapper
						.getValue(historical.get(i - 1)).subtract(
								mapper.getValue(historical.get(i))));
			else if (compareTo == -1)
				sumOfGain = sumOfGain.add(mapper.getValue(historical.get(i))
						.subtract(mapper.getValue(historical.get(i - 1))));

		}
		BigDecimal aveGain = sumOfGain.divide(N, FU.ROUND);
		BigDecimal aveLoss = sumOfLoss.divide(N, FU.ROUND);
		for (int i = n; i < historical.size(); i++) {

			int compareTo = mapper.getValue(historical.get(i - 1)).compareTo(
					mapper.getValue(historical.get(i)));
			if (compareTo == 1) {
				aveLoss = aveLoss
						.multiply(Nm1)
						.add(mapper.getValue(historical.get(i - 1)).subtract(
								mapper.getValue(historical.get(i))))
						.divide(N, FU.ROUND);

				aveGain = aveGain.multiply(Nm1).divide(N, FU.ROUND);

			} else if (compareTo == -1) {
				aveGain = aveGain
						.multiply(Nm1)
						.add(mapper.getValue(historical.get(i)).subtract(
								mapper.getValue(historical.get(i - 1))))
						.divide(N, FU.ROUND);

				aveLoss = aveLoss.multiply(Nm1).divide(N, FU.ROUND);
			} else {
				aveLoss = aveLoss.multiply(Nm1).divide(N, FU.ROUND);
				aveGain = aveGain.multiply(Nm1).divide(N, FU.ROUND);
			}

			BigDecimal rsi = FU.H100.subtract(FU.H100.divide(
					FU.U1.add(aveGain.divide(aveLoss, FU.ROUND)), FU.ROUND));

			results[rIndex++] = new TechnicalIndicator_(historical.get(i)
					.getDate(), "RSI", n + "DAY", rsi);

			System.out.println(historical.get(i).getDate() + "," + rsi);
		}

		return results;
	}
}
