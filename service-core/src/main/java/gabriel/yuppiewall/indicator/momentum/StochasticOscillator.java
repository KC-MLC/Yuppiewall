package gabriel.yuppiewall.indicator.momentum;

import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StochasticOscillator implements TechnicalIndicator {

	// http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:stochrsi
	// StochRSI = (RSI - Lowest Low RSI) / (Highest High RSI - Lowest Low RSI)
	@Override
	public TechnicalIndicator_[] calculate(StockDailySummary_[] historical,
			int day) {
		TechnicalIndicator_[] listOfRSI = new RSI().calculate(historical, day);
		TechnicalIndicator_[] results = new TechnicalIndicator_[listOfRSI.length
				- day];
		int rIndex = 0;
		for (int i = day; i < listOfRSI.length; i++) {

			TechnicalIndicator_ lowestRSI = find(-1, listOfRSI, i - day, day);
			TechnicalIndicator_ higestRSI = find(1, listOfRSI, i - day, day);

			BigDecimal stochRSI = listOfRSI[i]
					.getValue()
					.subtract(lowestRSI.getValue())
					.divide(higestRSI.getValue().subtract(lowestRSI.getValue()),
							RoundingMode.HALF_UP);
			results[rIndex++] = new TechnicalIndicator_(listOfRSI[i].getDate(),
					"SRSI", day + "DAY", stochRSI);
		}

		return results;
	}

	public TechnicalIndicator_ find(int HIGH_LOW, TechnicalIndicator_[] list,
			int startIndex, int endIndex) {
		TechnicalIndicator_ value = list[startIndex];
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
