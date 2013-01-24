package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.common.exception.InvalidParameterValueException;
import gabriel.yuppiewall.ds.domain.TechnicalIndicator_;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class CoreScanRunner implements ScanRunner {

	private static final String RUNNER_ID = CoreScanRunner.class.getName();

	@Override
	public ScanOutput[] runScan(ScanRequest scanRequest) {
		List<Condition> conditions = scanRequest.getConditions();
		for (Condition condition : conditions) {
			setTechnicalIndicator(condition.getLhs());
			setTechnicalIndicator(condition.getRhs());
		}
		Collection<Instrument> filteredList = getSymbols(scanRequest);
		Iterator<Instrument> itr = filteredList.iterator();

		while (itr.hasNext()) {
			Instrument symbol = itr.next();
			List<EndOfDayData> records = getSymbolEODRecord(symbol, scanRequest);

			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = conditions.get(i);
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
		ScanOutput[] retValue = new ScanOutput[scanRequest.getFilteredResult()
				.size()];
		itr = filteredList.iterator();
		int i = 0;
		while (itr.hasNext()) {
			Instrument key = itr.next();

			EndOfDayData eod = getSymbolEODRecord(key, scanRequest).get(0);
			retValue[i++] = new ScanOutput(key, eod);
		}
		return retValue;

	}

	private void setTechnicalIndicator(Expression exp) {

		if (exp.getIndicator() == null)
			throw new UnsupportedOperationException(
					"constant value not supported");
		TechnicalIndicator ti = getTechnicalIndicatorService()
				.getTechnicalIndicator(exp.getIndicator());
		exp.setTechnicalIndicator(ti);
	}

	protected List<EndOfDayData> getSymbolEODRecord(Instrument instrument,
			ScanRequest scanRequest) {
		return scanRequest.getInitialGrupedRecord().get(instrument);
	}

	protected Collection<Instrument> getSymbols(ScanRequest scanRequest) {

		return scanRequest.getFilteredResult();
	}

	private BigDecimal run(Expression exp, List<EndOfDayData> records) {
		TechnicalIndicator_[] result = exp.getTechnicalIndicator().calculate(
				records, exp);

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
