package gabriel.yuppiewall.indicator;

import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

import java.math.BigDecimal;

public interface TechnicalIndicator {

	BigDecimal calculate(StockDailySummary_[] historical, int day);

}
