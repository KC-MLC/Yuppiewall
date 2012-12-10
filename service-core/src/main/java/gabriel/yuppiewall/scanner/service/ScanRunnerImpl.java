package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.indicator.TechnicalIndicator.SCAN_ON;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.ScanParameter;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ScanRunnerImpl implements ScanRunner {

	@Override
	public void runScan(ScanParameter param, Principal requester) {
		String scanId = param.getScanId();

		Map<String, List<EndOfDayData_>> eodData = getEndOfDayDataRepository()
				.findRecords(param);
		List<Condition> conditions = param.getCondition();
		Iterator<String> itr = eodData.keySet().iterator();
		while (itr.hasNext()) {
			String symbol = itr.next();
			List<EndOfDayData_> records = eodData.get(symbol);
			for (Condition condition : conditions) {

				BigDecimal lhs = run(condition.getLHS(), records);
				BigDecimal rhs = run(condition.getRHS(), records);
				if (!operate(lhs, condition.getOperand(), rhs)) {
					itr.remove();
					records = null;
					break;
				}
			}
		}

	}

	private Object run(Condition lhs, List<EndOfDayData_> records,
			SCAN_ON scanOn) {
		if (lhs.getIndicator() == null)
			return null;
		TechnicalIndicator ti = getTechnicalIndicatorService()
				.getTechnicalIndicator(lhs.getIndicator());
		TechnicalIndicator_[] result = ti.calculate(records, lhs.getValue(),
				scanOn);

		return result[result.length - 1].getValue();

	}

	private EndOfDayDataRepository getEndOfDayDataRepository() {
		return null;
		// TODO Auto-generated method stub

	}

}
