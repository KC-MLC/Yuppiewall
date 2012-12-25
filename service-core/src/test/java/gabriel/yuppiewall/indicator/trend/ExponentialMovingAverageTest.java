package gabriel.yuppiewall.indicator.trend;

import gabriel.yuppiewall.indicator.SampleData;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;

import org.junit.Before;
import org.junit.Test;

public class ExponentialMovingAverageTest {

	private EndOfDayData_ historical[];

	@Before
	public void setUp() throws Exception {
		historical = SampleData.setup();
	}

	// test using
	// http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:moving_averages
	@Test
	public void testStockChart() {

		//new ExponentialMovingAverage().calculate(historical, 10);

	}

}
