package gabriel.yuppiewall.indicator.trend;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

public class SimpleMovingAverage implements TechnicalIndicator {

	void calculate(StockDailySummary_ today, StockDailySummary_ historical[],
			int days) {

		if (historical.length >= days) {
			long sum = 0;
			for (int index = 0; index <= days; index++) {
				sum += historical[index].getStockPriceLow().longValue();
			}
			sum = sum / days;
		}
	}

	void calculate(StockDailySummary_ historical[]) {

		for (int i = 1; i < historical.length; i++) {
			System.out.print(i + "\t");
			for (int j = 1; j <= i; j++) {

				BigDecimal sma = calculate(historical, i + 1, j + 1);
				store(sma, historical[i], j);

			}
			System.out.println();

		}

	}

	private void store(BigDecimal sma, StockDailySummary_ stockDailySummary_,
			int j) {
		System.out.print(sma + "\t");
	}

	public BigDecimal calculate(StockDailySummary_[] historical, int range,
			int day) {
		BigDecimal sum = new BigDecimal(0L);
		for (int i = range - day; i < range; i++) {
			sum = sum.add(historical[i].getStockPriceLow());
		}

		return sum.divide(new BigDecimal(day));

	}

	@Override
	public BigDecimal calculate(StockDailySummary_[] historical, int day) {
		return calculate(historical, historical.length, day);

	}

	public static void main(String[] args) {

		StockDailySummary_ historical[] = new StockDailySummary_[6];
		for (int i = 0; i < historical.length; i++) {
			historical[i] = new StockDailySummary_("", "", new Date(),
					new BigDecimal(0L), new BigDecimal(0L), new BigDecimal(i),
					new BigDecimal(0L), new BigInteger("0"), new BigDecimal(0L));

		}
		new SimpleMovingAverage().calculate(historical);
	}
}
