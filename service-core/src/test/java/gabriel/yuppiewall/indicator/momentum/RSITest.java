package gabriel.yuppiewall.indicator.momentum;

import gabriel.yuppiewall.indicator.SampleData;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import org.junit.Before;
import org.junit.Test;

public class RSITest {

	private EndOfDayData historical[];

	@Before
	public void setUp() throws Exception {
		historical = SampleData.setupRSI();
	}

	@Test
	public void test() {
		//new RSI().calculate(historical, 14);
	}

}
