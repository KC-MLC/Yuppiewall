package gabriel.yuppiewall.indicator.trend;

import gabriel.yuppiewall.indicator.SampleData;
import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class SimpleMovingAverageTest {

	private StockDailySummary_ historical[];

	@Before
	public void setUp() throws Exception {
		historical = SampleData.setup();
	}

	@Test
	public void test2day() {

		historical = new StockDailySummary_[6];
		for (int i = 0; i < historical.length; i++) {
			historical[i] = new StockDailySummary_("", "", new Date(),
					new BigDecimal(0L), new BigDecimal(0L), new BigDecimal(i),
					new BigDecimal(0L), new BigInteger("0"), new BigDecimal(0L));

		}

		BigDecimal sma = new SimpleMovingAverage().calculate(historical, 2);
		Assert.assertEquals(new BigDecimal(4.5), sma);

		sma = new SimpleMovingAverage().calculate(historical, 3);
		Assert.assertEquals(new BigDecimal(4), sma);

		sma = new SimpleMovingAverage().calculate(historical, 4);
		Assert.assertEquals(new BigDecimal(3.5), sma);

		sma = new SimpleMovingAverage().calculate(historical, 5);
		Assert.assertEquals(new BigDecimal(3), sma);

		sma = new SimpleMovingAverage().calculate(historical, 6);
		Assert.assertEquals(new BigDecimal(2.5), sma);

	}

	// test using
	// http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:moving_averages
	@Test
	public void testStockChart() {
		BigDecimal sma = new SimpleMovingAverage().calculate(historical, 10);
		Assert.assertEquals(new BigDecimal(23.13).floatValue(),
				round(sma.floatValue()));
	}

	@Test
	public void testAllStockChart() {
		BigDecimal sma = new SimpleMovingAverage().calculate(historical, 10);
		Assert.assertEquals(new BigDecimal(23.13).floatValue(),
				round(sma.floatValue()));
		// new SimpleMovingAverage().calculate(historical);
		new SimpleMovingAverage().calculateSet(historical, 10);
	}

	public static float round(float val) {
		float p = (float) Math.pow(10, 2);
		val = val * p;
		float tmp = Math.round(val);
		return (float) tmp / p;
	}
}
