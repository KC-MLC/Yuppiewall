package gabriel.yuppiewall.indicator.momentum;

import gabriel.yuppiewall.ds.domain.TechnicalIndicatorOutput;
import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.Expression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class StochasticOscillator implements TechnicalIndicator {

	// http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:stochrsi
	// StochRSI = (RSI - Lowest Low RSI) / (Highest High RSI - Lowest Low RSI)
	@Override
	public TechnicalIndicatorOutput[] calculate(List<EndOfDayData> historical,
			Expression exp) {
		int day = Integer.parseInt(exp.getParameters());
		TechnicalIndicatorOutput[] listOfRSI = new RSI().calculate(historical, exp);
		TechnicalIndicatorOutput[] results = new TechnicalIndicatorOutput[listOfRSI.length
				- day];
		int rIndex = 0;
		for (int i = day; i < listOfRSI.length; i++) {

			TechnicalIndicatorOutput lowestRSI = find(-1, listOfRSI, i - day, day);
			TechnicalIndicatorOutput higestRSI = find(1, listOfRSI, i - day, day);

			BigDecimal stochRSI = listOfRSI[i]
					.getValue()
					.subtract(lowestRSI.getValue())
					.divide(higestRSI.getValue().subtract(lowestRSI.getValue()),
							RoundingMode.HALF_UP);
			results[rIndex++] = new TechnicalIndicatorOutput(listOfRSI[i].getDate(),
					"SRSI", day + "DAY", stochRSI);
		}

		return results;
	}

	public TechnicalIndicatorOutput find(int HIGH_LOW, TechnicalIndicatorOutput[] list,
			int startIndex, int endIndex) {
		TechnicalIndicatorOutput value = list[startIndex];
		for (int i = startIndex + 1; i < endIndex; i++) {
			if (HIGH_LOW == 1) {
				if (value.compareTo(list[i]) != 1) {
					value = list[i];
				}
			} else {
				if (value.compareTo(list[i]) == 1) {
					value = list[i];
				}
			}

		}
		return value;
	}
}
