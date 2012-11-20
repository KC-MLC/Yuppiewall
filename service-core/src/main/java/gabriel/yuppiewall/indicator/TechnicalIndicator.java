package gabriel.yuppiewall.indicator;

import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

public interface TechnicalIndicator {

	TechnicalIndicator_[] calculate(StockDailySummary_[] historical, int day);

}
