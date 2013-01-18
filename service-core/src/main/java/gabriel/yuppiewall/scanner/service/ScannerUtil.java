package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.common.exception.InvalidParameterValueException;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
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

public class ScannerUtil implements ScanRunner {

	@Override
	public List<ScanOutput> runScan(List<Condition> conditions,
			ScanRequest scanRequest) {
		Iterator<String> itr = getSymbols(scanRequest);

		while (itr.hasNext()) {
			String symbol = itr.next();
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
		itr = scanRequest.getFilteredResult().iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			EndOfDayData eod = scanRequest.getInitialGrupedRecord().get(key)
					.get(0);
			retValue.add(new ScanOutput(eod.getStockSymbol(), null, eod
					.getExchange().getName(), null, null, eod
					.getStockPriceOpen(), eod.getStockPriceHigh(), eod
					.getStockPriceLow(), eod.getStockPriceClose(), eod
					.getStockVolume()));
		}
		return retValue;

	}

	protected List<EndOfDayData> getSymbolEODRecord(String symbol,
			ScanRequest scanRequest) {
		return scanRequest.getInitialGrupedRecord().get(symbol);
	}

	protected Iterator<String> getSymbols(ScanRequest scanRequest) {

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
}
