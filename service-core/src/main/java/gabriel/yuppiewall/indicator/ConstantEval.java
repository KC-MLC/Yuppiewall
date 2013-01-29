package gabriel.yuppiewall.indicator;

import gabriel.yuppiewall.common.exception.InvalidParameterValueException;
import gabriel.yuppiewall.ds.domain.TechnicalIndicatorOutput;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.Expression;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConstantEval implements TechnicalIndicator {

	public ConstantEval() {
	}

	@Override
	public TechnicalIndicatorOutput[] calculate(List<EndOfDayData> historical,
			Expression exp) {
		final EndOfDayDataScanOnValue mapper = EndOfDayDataScanOnValue
				.getMapper(exp.getScanOn());
		String param = exp.getParameters();
		int size = Integer.parseInt(param);
		int ofset = exp.getOffset();
		if (size + ofset > historical.size())
			throw new InvalidParameterValueException(
					"parameter exceeds data set max data set is" + size);
		historical = historical.subList(historical.size() - (size + ofset),
				historical.size() - ofset);
		EndOfDayData max = null;
		if (historical.size() == 1) {
			max = historical.get(0);
		} else {
			max = Collections.max(historical, new Comparator<EndOfDayData>() {

				@Override
				public int compare(EndOfDayData o1, EndOfDayData o2) {
					BigDecimal v1 = mapper.getValue(o1);
					BigDecimal v2 = mapper.getValue(o2);

					return v1.compareTo(v2);
				}
			});
		}

		return new TechnicalIndicatorOutput[] { new TechnicalIndicatorOutput(
				max.getDate(), "CONSTANT", "MAX", mapper.getValue(max)) };
	}
}
