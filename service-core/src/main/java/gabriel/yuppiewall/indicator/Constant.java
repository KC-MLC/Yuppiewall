package gabriel.yuppiewall.indicator;

import gabriel.yuppiewall.ds.domain.TechnicalIndicatorOutput;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.Expression;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Constant implements TechnicalIndicator, Serializable {

	@Override
	public TechnicalIndicatorOutput[] calculate(List<EndOfDayData> historical,
			Expression exp) {

		return new TechnicalIndicatorOutput[] { new TechnicalIndicatorOutput(
				new Date(), "CONSTANT", "CONSTANT", new BigDecimal(
						exp.getParameters())) };
	}

}
