package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.indicator.service.SimpleTechnicalIndicatorService;
import gabriel.yuppiewall.indicator.service.TechnicalIndicatorService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.ScanParameter;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ScannerTest {

	private ScannerServive scanner;

	@Before
	public void setUp() throws Exception {
		scanner = new ScannerServiceImpl() {

			@Override
			protected TechnicalIndicatorService getTechnicalIndicatorService() {
				return new SimpleTechnicalIndicatorService();
			}

			@Override
			protected EndOfDayDataRepository getEndOfDayDataRepository() {
				// TODO Auto-generated method stub
				return new EndOfDayDataRepository() {

					@Override
					public void createEndOfDayData(EndOfDayData endOfDayData) {
						// TODO Auto-generated method stub

					}

					@Override
					public void createEndOfDayData(List<EndOfDayData> list) {
						// TODO Auto-generated method stub

					}

					@Override
					public ScanRequest createScanRequest(
							ScanParameter param) {/*
						Map<String, List<EndOfDayData>> list = new HashMap<>();
						{
							EndOfDayData[] li;
							try {
								li = SampleData.setup();
								List<EndOfDayData> l = Arrays.asList(li);
								list.put("GOOG", l);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						return list;
					*/return null;}
				};
			}

			@Override
			protected ScanRunner getScanRunner() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@Test
	public void testBasicParse() {
		/*ScanParameter sp = new ScanParameter()
				.Builder()
				.fromExchange(new Exchange("NYSE"))
				.addCondition(
						new Expression("sma", PERIOD.DAILY, new BigDecimal(20),
								SCAN_ON.VOLUME), OPERAND.GT,
						new Expression(new BigDecimal(40000))).build();
		scanner.runScan(sp);
*/
	}
}
