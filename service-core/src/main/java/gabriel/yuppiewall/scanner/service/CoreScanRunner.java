package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.common.exception.InvalidParameterValueException;
import gabriel.yuppiewall.ds.domain.TechnicalIndicatorOutput;
import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.indicator.service.TechnicalIndicatorService;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.Expression;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.domain.ScanParameter.OPERAND;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class CoreScanRunner implements ScanRunner {

	private static final String RUNNER_ID = CoreScanRunner.class.getName();

	@Override
	public List<ScanOutput> runScan(ScanRequest scanRequest) {
		List<Condition> conditions = scanRequest.getConditions();
		for (Condition condition : conditions) {
			setTechnicalIndicator(condition.getLhs());
			setTechnicalIndicator(condition.getRhs());
		}
		LinkedList<Instrument> filteredList = getInstrumentFromExchange(scanRequest
				.getExchanges());
		List<ScanOutput> result = new ArrayList<>();
		Iterator<Instrument> itr = filteredList.iterator();

		while (itr.hasNext()) {
			Instrument symbol = itr.next();
			List<EndOfDayData> records = getSymbolEODRecord(symbol);
			if (records == null) {
				itr.remove();
				continue;
			}
			boolean passed = true;
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = conditions.get(i);
				Expression lhs = condition.getLhs();
				Expression rhs = condition.getRhs();
				try {
					BigDecimal lValue = run(lhs, records);
					BigDecimal rValue = run(rhs, records);
					if (!operate(lValue, condition.getOperand(), rValue)) {
						itr.remove();
						passed = false;
						break;
					}
				} catch (InvalidParameterValueException ipve) {
					itr.remove();
					passed = false;
					System.out.println(symbol);
					break;
				}
			}
			if (passed) {
				result.add(new ScanOutput(records.get(0)));
			}
			records = null;
		}
		return result;

	}

	private void setTechnicalIndicator(Expression exp) {

		if (exp.getIndicator() == null)
			throw new UnsupportedOperationException(
					"constant value not supported");
		TechnicalIndicator ti = getTechnicalIndicatorService()
				.getTechnicalIndicator(exp.getIndicator());
		exp.setTechnicalIndicator(ti);
	}

	protected abstract List<EndOfDayData> getSymbolEODRecord(
			Instrument instrument);

	protected abstract LinkedList<Instrument> getInstrumentFromExchange(
			Set<String /* symbol */> exchanges);

	private BigDecimal run(Expression exp, List<EndOfDayData> records) {
		TechnicalIndicatorOutput[] result = exp.getTechnicalIndicator()
				.calculate(records, exp);

		return result[result.length - 1].getValue();

	}

	protected abstract TechnicalIndicatorService getTechnicalIndicatorService();

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

	@Override
	public String getId() {
		return RUNNER_ID;
	}
}
