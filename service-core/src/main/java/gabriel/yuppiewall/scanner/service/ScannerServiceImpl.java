package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.indicator.service.TechnicalIndicatorService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.Expression;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.scanner.domain.ScanParameter.OPERAND;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class ScannerServiceImpl implements ScannerServive {

	@Override
	public List<EndOfDayData> runScan(final ScanParameter param,
			final PrimaryPrincipal requester) {

		// get group filter
		Map<String, List<EndOfDayData>> eodData = getEndOfDayDataRepository()
				.findRecords(param);
		List<Condition> conditions = param.getConditions();
		if (conditions == null)
			conditions = new ArrayList<>();
		Iterator<String> itr = eodData.keySet().iterator();
		List<EndOfDayData> retValue = new ArrayList<>();
		while (itr.hasNext()) {
			String symbol = itr.next();
			List<EndOfDayData> records = eodData.get(symbol);
			boolean success = true;
			for (Condition condition : conditions) {

				Expression lhs = condition.getLhs();
				Expression rhs = condition.getRhs();

				BigDecimal lValue = run(lhs, records);
				BigDecimal rValue = run(rhs, records);
				if (!operate(lValue, condition.getOperand(), rValue)) {
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

	private BigDecimal run(Expression exp, List<EndOfDayData> records) {
		if (exp.getIndicator() == null)
			throw new UnsupportedOperationException(
					"constant value not supported");// return new
													// BigDecimal(exp.getValue());

		TechnicalIndicator ti = getTechnicalIndicatorService()
				.getTechnicalIndicator(exp.getIndicator());
		TechnicalIndicator_[] result = ti.calculate(records, exp);

		return result[result.length - 1].getValue();

	}

	protected abstract TechnicalIndicatorService getTechnicalIndicatorService();

	protected abstract EndOfDayDataRepository getEndOfDayDataRepository();

}
