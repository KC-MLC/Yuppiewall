package gabriel.yuppiewall.indicator.momentum;

import gabriel.yuppiewall.indicator.SampleData;
import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

import org.junit.Before;
import org.junit.Test;

public class RSITest {

	private StockDailySummary_ historical[];

	@Before
	public void setUp() throws Exception {
		historical = SampleData.setupRSI();
	}

	@Test
	public void test() {
		new RSI().calculate(historical, 14);
	}

}
