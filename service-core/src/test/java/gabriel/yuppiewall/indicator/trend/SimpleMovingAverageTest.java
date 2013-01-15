package gabriel.yuppiewall.indicator.trend;

import gabriel.yuppiewall.indicator.SampleData;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class SimpleMovingAverageTest {

	private EndOfDayData historical[];

	@Before
	public void setUp() throws Exception {
		historical = SampleData.setup();
	}

	@Test
	public void test2day() {

		historical = new EndOfDayData[6];
		for (int i = 0; i < historical.length; i++) {
			historical[i] = new EndOfDayData(new Exchange(""), "",
					new Date(), new BigDecimal(0L), new BigDecimal(0L),
					new BigDecimal(i), new BigDecimal(0L), new BigDecimal("0"),
					new BigDecimal(0L));

		}

		TechnicalIndicator_[] sma = null;/*
										 * new SimpleMovingAverage().calculate(
										 * historical, 2);
										 */
		Assert.assertEquals(new BigDecimal(4.5), sma[sma.length - 1].getValue());

		/* sma = new SimpleMovingAverage().calculate(historical, 3); */
		Assert.assertEquals(new BigDecimal(4), sma[sma.length - 1].getValue());

		//sma = new SimpleMovingAverage().calculate(historical, 4);
		Assert.assertEquals(new BigDecimal(3.5), sma[sma.length - 1].getValue());

		//sma = new SimpleMovingAverage().calculate(historical, 5);
		Assert.assertEquals(new BigDecimal(3), sma[sma.length - 1].getValue());

		//sma = new SimpleMovingAverage().calculate(historical, 6);
		Assert.assertEquals(new BigDecimal(2.5), sma[sma.length - 1].getValue());

	}

	// test using
	// http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:moving_averages
	@Test
	public void testStockChart() {
		TechnicalIndicator_[] sma = null/*new SimpleMovingAverage().calculate(
				historical, 10)*/;
		Assert.assertEquals(new BigDecimal(23.13).floatValue(),
				round(sma[sma.length - 1].getValue().floatValue()));
	}

	@Test
	public void testAllStockChart() {
		TechnicalIndicator_[] sma = null/*new SimpleMovingAverage().calculate(
				historical, 10)*/;
		Assert.assertEquals(new BigDecimal(23.13).floatValue(),
				round(sma[sma.length - 1].getValue().floatValue()));
		// new SimpleMovingAverage().calculate(historical);
		//new SimpleMovingAverage().calculateSet(historical, 10);
	}

	public static float round(float val) {
		float p = (float) Math.pow(10, 2);
		val = val * p;
		float tmp = Math.round(val);
		return (float) tmp / p;
	}
}
