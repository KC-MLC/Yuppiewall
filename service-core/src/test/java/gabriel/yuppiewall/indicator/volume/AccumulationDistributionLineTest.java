package gabriel.yuppiewall.indicator.volume;

import gabriel.yuppiewall.indicator.SampleData;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import org.junit.Before;
import org.junit.Test;

public class AccumulationDistributionLineTest {

	private EndOfDayData historical[];

	@Before
	public void setUp() throws Exception {
		historical = SampleData.setupADL();
	}

	@Test
	public void testSet() {

		//new AccumulationDistributionLine().calculate(historical, 0);

	}
}
