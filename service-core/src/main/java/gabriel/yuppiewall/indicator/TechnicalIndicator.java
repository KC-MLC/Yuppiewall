package gabriel.yuppiewall.indicator;

import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.math.BigDecimal;
import java.util.List;

public interface TechnicalIndicator {

	static abstract class EndOfDayDataScanOnValue {
		abstract public BigDecimal getValue(EndOfDayData data);

		public static EndOfDayDataScanOnValue getMapper(SCAN_ON scanON) {
			if (scanON == SCAN_ON.VOLUME)
				return volume;
			return null;
		}
	}

	EndOfDayDataScanOnValue volume = new EndOfDayDataScanOnValue() {

		@Override
		public BigDecimal getValue(EndOfDayData data) {
			return data.getStockVolume();
		}
	};

	public enum SCAN_ON {
		VOLUME, CLOSING
	}

	TechnicalIndicator_[] calculate(List<EndOfDayData> historical, int day,
			SCAN_ON scanON);

}
