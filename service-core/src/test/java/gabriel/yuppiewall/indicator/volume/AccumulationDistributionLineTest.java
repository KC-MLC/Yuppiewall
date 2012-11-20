package gabriel.yuppiewall.indicator.volume;

import gabriel.yuppiewall.indicator.SampleData;
import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

import org.junit.Before;
import org.junit.Test;

public class AccumulationDistributionLineTest {

	private StockDailySummary_ historical[];

	@Before
	public void setUp() throws Exception {
		historical = SampleData.setupADL();
	}

	@Test
	public void testSet() {

		new AccumulationDistributionLine().calculate(historical, 0);

	}
}
