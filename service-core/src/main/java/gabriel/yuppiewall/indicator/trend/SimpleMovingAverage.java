package gabriel.yuppiewall.indicator.trend;

import gabriel.yuppiewall.common.FU;
import gabriel.yuppiewall.common.exception.InvalidParameterValueException;
import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.Expression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class SimpleMovingAverage implements TechnicalIndicator {

	void calculate(EndOfDayData today, EndOfDayData historical[], int days) {

		if (historical.length >= days) {
			long sum = 0;
			for (int index = 0; index <= days; index++) {
				sum += historical[index].getStockPriceAdjClose().longValue();
			}
			sum = sum / days;
		}
	}

	/*
	 * public void calculate(EndOfDayData_ historical[]) {
	 * 
	 * for (int i = 0; i < historical.length; i++) { System.out.print(i + "\t");
	 * for (int j = 1; j <= i; j++) {
	 * 
	 * BigDecimal sma = calculate(historical, i + 1, j + 1); store(sma,
	 * historical[i], j + 1, i + 1);
	 * 
	 * } System.out.println();
	 * 
	 * }
	 * 
	 * }
	 */

	protected void store(BigDecimal sma, EndOfDayData stockDailySummary_,
			int n, int day) {
		System.out.print(stockDailySummary_.getDate() + "," + sma + "(" + n
				+ "," + day + ")" + "\t");
	}

	public BigDecimal calculate(List<EndOfDayData> historical, int range,
			int day, EndOfDayDataScanOnValue mapper) {
		BigDecimal sum = FU.U0;
		for (int i = range - day; i < range; i++) {
			sum = sum.add(mapper.getValue(historical.get(i)));
		}

		return sum.divide(new BigDecimal(day), RoundingMode.HALF_UP);

	}

	@Override
	public TechnicalIndicator_[] calculate(List<EndOfDayData> historical,
			Expression exp) {
		EndOfDayDataScanOnValue mapper = EndOfDayDataScanOnValue.getMapper(exp
				.getScanOn());
		String param = exp.getParameters();
		int size = Integer.parseInt(param);
		int ofset = exp.getOffset();

		if (size + ofset > historical.size()) {
			throw new InvalidParameterValueException(
					"parameter exceeds data set max data set is" + size);
		}
		historical = historical.subList(ofset, size);

		BigDecimal sum = FU.U0;
		for (int i = 0, max = historical.size(); i < max; i++) {
			sum = sum.add(mapper.getValue(historical.get(i)));
		}
		BigDecimal sma = sum.divide(new BigDecimal(historical.size()),
				RoundingMode.HALF_UP);

		return new TechnicalIndicator_[] { new TechnicalIndicator_(historical
				.get(historical.size() - 1).getDate(), "SMA", ofset + "DAY",
				sma) };
	}

	// @Override
	/*
	 * public void calculateSet(EndOfDayData_[] historical, int day) {
	 * 
	 * for (int i = day - 1; i < historical.length; i++) { BigDecimal val =
	 * calculate(historical, i + 1, day);
	 * System.out.println(historical[i].getDate() + "," + val + "(" + day + ","
	 * + i + ")"); }
	 * 
	 * }
	 */

	public static void main(String[] args) {

		/*
		 * EndOfDayData_ historical[] = new EndOfDayData_[6]; for (int i = 0; i
		 * < historical.length; i++) { historical[i] = new EndOfDayData_("", "",
		 * new Date(), new BigDecimal(0L), new BigDecimal(0L), new
		 * BigDecimal(i), new BigDecimal(0L), new BigInteger("0"), new
		 * BigDecimal(0L));
		 * 
		 * } new SimpleMovingAverage().calculate(historical);
		 */
	}
}
