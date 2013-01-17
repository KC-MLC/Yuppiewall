package gabriel.yuppiewall.indicator;

import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.Expression;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Constant implements TechnicalIndicator {

	@Override
	public TechnicalIndicator_[] calculate(List<EndOfDayData> historical,
			Expression exp) {

		return new TechnicalIndicator_[] { new TechnicalIndicator_(new Date(),
				"CONSTANT", "CONSTANT", new BigDecimal(exp.getParameters())) };
	}

}
