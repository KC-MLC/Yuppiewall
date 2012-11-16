package gabriel.yuppiewall.indicator.trend;

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
		historical = new StockDailySummary_[6];
		for (int i = 0; i < historical.length; i++) {
			historical[i] = new StockDailySummary_("", "", new Date(),
					new BigDecimal(0L), new BigDecimal(0L), new BigDecimal(i),
					new BigDecimal(0L), new BigInteger("0"), new BigDecimal(0L));

		}
	}

	@Test
	public void test2day() {

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
}
