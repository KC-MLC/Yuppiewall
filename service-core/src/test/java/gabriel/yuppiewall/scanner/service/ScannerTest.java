package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.indicator.SampleData;
import gabriel.yuppiewall.indicator.service.SimpleTechnicalIndicatorService;
import gabriel.yuppiewall.indicator.service.TechnicalIndicatorService;
import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.Expression;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.scanner.domain.ScanParameter.OPERAND;
import gabriel.yuppiewall.scanner.domain.ScanParameter.PERIOD;
import gabriel.yuppiewall.scanner.domain.ScanParameter.SCAN_ON;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
					public void createEndOfDayData(EndOfDayData_ endOfDayData) {
						// TODO Auto-generated method stub

					}

					@Override
					public void createEndOfDayData(List<EndOfDayData_> list) {
						// TODO Auto-generated method stub

					}

					@Override
					public Map<String, List<EndOfDayData_>> findRecords(
							ScanParameter param) {
						Map<String, List<EndOfDayData_>> list = new HashMap<>();
						{
							EndOfDayData_[] li;
							try {
								li = SampleData.setup();
								List<EndOfDayData_> l = Arrays.asList(li);
								list.put("GOOG", l);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						return list;
					}
				};
			}
		};
	}

	@Test
	public void testBasicParse() {
		ScanParameter sp = new ScanParameter()
				.Builder()
				.fromExchange(new Exchange_("NYSE"))
				.addCondition(
						new Expression("sma", PERIOD.DAILY, new BigDecimal(20),
								SCAN_ON.VOLUME), OPERAND.GT,
						new Expression(new BigDecimal(40000))).build();
		scanner.runScan(sp);

	}
}
