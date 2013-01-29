package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;

import org.junit.Before;
import org.junit.Test;

public class ScannerTest {

	private ScannerServive scanner;

	@Before
	public void setUp() throws Exception {
		scanner = new ScannerServiceImpl() {

			@Override
			protected ScanRunner getScanRunner() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected SystemDataRepository getSystemDataRepository() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@Test
	public void testBasicParse() {
		/*
		 * ScanParameter sp = new ScanParameter() .Builder() .fromExchange(new
		 * Exchange("NYSE")) .addCondition( new Expression("sma", PERIOD.DAILY,
		 * new BigDecimal(20), SCAN_ON.VOLUME), OPERAND.GT, new Expression(new
		 * BigDecimal(40000))).build(); scanner.runScan(sp);
		 */
	}
}
