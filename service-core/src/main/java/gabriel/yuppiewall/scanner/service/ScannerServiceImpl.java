package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.indicator.service.TechnicalIndicatorService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.scanner.domain.ScanParameter.OPERAND;
import gabriel.yuppiewall.scanner.domain.ScanParameter.SCAN_ON;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class ScannerServiceImpl implements ScannerServive {

	@Override
	public List<EndOfDayData_> runScan(final ScanParameter param,
			final Principal requester) {

		Map<String, List<EndOfDayData_>> eodData = getEndOfDayDataRepository()
				.findRecords(param);
		List<Condition> conditions = param.getCondition();
		Iterator<String> itr = eodData.keySet().iterator();
		List<EndOfDayData_> retValue = new ArrayList<>();
		while (itr.hasNext()) {
			String symbol = itr.next();
			List<EndOfDayData_> records = eodData.get(symbol);
			boolean success = true;
			for (Condition condition : conditions) {

				Condition lhsCondition = condition.getLHS();
				Condition rhsCondition = condition.getRHS();

				BigDecimal lhs = run(lhsCondition, records,
						lhsCondition.getScanOn());
				BigDecimal rhs = run(rhsCondition.getRHS(), records,
						rhsCondition.getScanOn());
				if (!operate(lhs, condition.getOperand(), rhs)) {
					itr.remove();
					records = null;
					success = false;
					break;
				}
			}
			if (success)
				retValue.add(records.get(records.size() - 1));
		}
		return retValue;

	}

	private boolean operate(BigDecimal lhs, OPERAND operand, BigDecimal rhs) {
		switch (operand) {
		case EQUAL:
			return (lhs.compareTo(rhs) == 0);
		case GT:
			return (lhs.compareTo(rhs) > 0);
		case LT:
			return (lhs.compareTo(rhs) < 0);
		}
		return false;
	}

	private BigDecimal run(Condition lhs, List<EndOfDayData_> records,
			SCAN_ON scanOn) {
		if (lhs.getIndicator() == null)
			return null;
		TechnicalIndicator ti = getTechnicalIndicatorService()
				.getTechnicalIndicator(lhs.getIndicator());
		TechnicalIndicator_[] result = ti.calculate(records, lhs.getValue(),
				convert(scanOn));

		return result[result.length - 1].getValue();

	}

	protected abstract TechnicalIndicatorService getTechnicalIndicatorService();

	private static gabriel.yuppiewall.indicator.TechnicalIndicator.SCAN_ON convert(
			SCAN_ON scanOn) {
		switch (scanOn) {
		case VOLUME:
			return gabriel.yuppiewall.indicator.TechnicalIndicator.SCAN_ON.VOLUME;
		case CLOSING:
			return gabriel.yuppiewall.indicator.TechnicalIndicator.SCAN_ON.CLOSING;
		}
		return null;
	}

	protected abstract EndOfDayDataRepository getEndOfDayDataRepository();

}
