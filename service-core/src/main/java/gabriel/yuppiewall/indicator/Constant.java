package gabriel.yuppiewall.indicator;

import gabriel.yuppiewall.ds.domain.TechnicalIndicator_;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.Expression;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Constant implements TechnicalIndicator, Serializable {

	@Override
	public TechnicalIndicator_[] calculate(List<EndOfDayData> historical,
			Expression exp) {

		return new TechnicalIndicator_[] { new TechnicalIndicator_(new Date(),
				"CONSTANT", "CONSTANT", new BigDecimal(exp.getParameters())) };
	}

}
