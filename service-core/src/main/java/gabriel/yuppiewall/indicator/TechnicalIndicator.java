package gabriel.yuppiewall.indicator;

import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;

public interface TechnicalIndicator {

	TechnicalIndicator_[] calculate(EndOfDayData_[] historical, int day);

}
