package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.common.exception.InvalidParameterValueException;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.indicator.trend.SimpleMovingAverage;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.ScanResult;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.Expression;
import gabriel.yuppiewall.scanner.domain.ScanParameter.OPERAND;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class ScannerUtil {

	public static void filter(Condition condition, ScanResult groupedValue) {

		SimpleMovingAverage sma = new SimpleMovingAverage();

		Iterator<String> itr = groupedValue.getFilteredResult().iterator();

		while (itr.hasNext()) {
			String symbol = itr.next();
			List<EndOfDayData> tempList = groupedValue.getInitialGrupedRecord()
					.get(symbol);
			TechnicalIndicator_[] res;

			try {
				res = sma.calculate(tempList, condition.getLhs());
			} catch (InvalidParameterValueException ipve) {
				itr.remove();
				System.out.println(symbol);
				continue;
			}
			if (0 >= res[0].getValue().compareTo(
					new BigDecimal(condition.getRhs().getParameters()))) {
				itr.remove();
			}
		}
	}

	public static void filter(List<Condition> conditions, ScanResult eodData) {
		Iterator<String> itr = eodData.getFilteredResult().iterator();

		while (itr.hasNext()) {
			String symbol = itr.next();
			List<EndOfDayData> records = eodData.getInitialGrupedRecord().get(
					symbol);
			for (Condition condition : conditions) {

				Expression lhs = condition.getLhs();
				Expression rhs = condition.getRhs();
				try {
					BigDecimal lValue = run(lhs, records);
					BigDecimal rValue = run(rhs, records);
					if (!operate(lValue, condition.getOperand(), rValue)) {
						itr.remove();
						records = null;
						break;
					}
				} catch (InvalidParameterValueException ipve) {
					itr.remove();
					records = null;
					System.out.println(symbol);
					break;
				}

			}
		}

	}

	private static BigDecimal run(Expression exp, List<EndOfDayData> records) {
		TechnicalIndicator_[] result = exp.getTechnicalIndicator().calculate(
				records, exp);

		return result[result.length - 1].getValue();

	}

	private static boolean operate(BigDecimal lhs, OPERAND operand,
			BigDecimal rhs) {
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
}
