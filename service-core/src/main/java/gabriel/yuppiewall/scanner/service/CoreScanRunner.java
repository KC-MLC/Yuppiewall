package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.common.exception.InvalidParameterValueException;
import gabriel.yuppiewall.ds.domain.TechnicalIndicator_;
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
import java.util.List;

public class CoreScanRunner implements ScanRunner {

	private static final String RUNNER_ID = CoreScanRunner.class.getName();

	@Override
	public List<ScanOutput> runScan(List<Condition> conditions,
			ScanRequest scanRequest) {
		Iterator<Instrument> itr = getSymbols(scanRequest);

		while (itr.hasNext()) {
			Instrument symbol = itr.next();
			List<EndOfDayData> records = getSymbolEODRecord(symbol, scanRequest);
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
		List<ScanOutput> retValue = new ArrayList<ScanOutput>(scanRequest
				.getFilteredResult().size());
		Iterator<Instrument> filteredResult = scanRequest.getFilteredResult()
				.iterator();
		while (filteredResult.hasNext()) {
			Instrument key = filteredResult.next();
			EndOfDayData eod = scanRequest.getInitialGrupedRecord().get(key)
					.get(0);
			retValue.add(new ScanOutput(key, eod));
		}
		return retValue;

	}

	protected List<EndOfDayData> getSymbolEODRecord(Instrument instrument,
			ScanRequest scanRequest) {
		return scanRequest.getInitialGrupedRecord().get(instrument);
	}

	protected Iterator<Instrument> getSymbols(ScanRequest scanRequest) {

		return scanRequest.getFilteredResult().iterator();
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

	@Override
	public String getId() {
		return RUNNER_ID;
	}
}
