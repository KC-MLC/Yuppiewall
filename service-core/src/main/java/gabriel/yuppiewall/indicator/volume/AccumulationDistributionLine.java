package gabriel.yuppiewall.indicator.volume;

import gabriel.yuppiewall.common.FU;
import gabriel.yuppiewall.indicator.TechnicalIndicator;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.math.BigDecimal;
import java.util.List;

public class AccumulationDistributionLine implements TechnicalIndicator {

	// http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:accumulation_distrib

	// 1. Money Flow Multiplier = [(Close - Low) - (High - Close)] /(High - Low)
	// 2. Money Flow Volume = Money Flow Multiplier x Volume for the Period
	// 3. ADL = Previous ADL + Current Period's Money Flow Volume
	@Override
	public TechnicalIndicator_[] calculate(List<EndOfDayData> historical,
			int day, SCAN_ON ignore) {

		BigDecimal ADL = FU.U0;

		for (int i = 0; i < historical.size(); i++) {
			EndOfDayData t = historical.get(i);
			// t.getStockPriceAdjClose().subtract(subtrahend)
			/*
			 * BigDecimal cml = t.getStockPriceAdjClose().subtract(
			 * t.getStockPriceLow()); System.out.println("CML=" + cml);
			 */
			BigDecimal mfm = ((t.getStockPriceAdjClose().subtract(t
					.getStockPriceLow())).subtract(t.getStockPriceHigh()
					.subtract(t.getStockPriceAdjClose()))).divide(t
					.getStockPriceHigh().subtract(t.getStockPriceLow()),
					FU.ROUND);

			BigDecimal mfv = mfm.multiply(t.getStockVolume());
			ADL = ADL.add(mfv);
			System.out.println(t.getDate() + "," /* + mfm + "," + mfv + "," */
					+ ADL);

		}

		return null;
	}
}
