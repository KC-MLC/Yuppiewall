package gabriel.yuppiewall.indicator;

import gabriel.yuppiewall.ds.domain.TechnicalIndicator_;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.Expression;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface TechnicalIndicator extends Serializable {

	static abstract class EndOfDayDataScanOnValue {
		abstract public BigDecimal getValue(EndOfDayData data);

		public static EndOfDayDataScanOnValue getMapper(SCAN_ON scanON) {

			switch (scanON) {
			case VOLUME:
				return volume;
			case CLOSING:
				return closing;
			case HIGH:
				return high;
			case LOW:
				return low;
			case OPEN:
				return open;
			default:
				throw new UnsupportedOperationException("Not yet implemented ");
			}
		}
	}

	EndOfDayDataScanOnValue volume = new EndOfDayDataScanOnValue() {

		@Override
		public BigDecimal getValue(EndOfDayData data) {
			return data.getStockVolume();
		}
	};
	EndOfDayDataScanOnValue closing = new EndOfDayDataScanOnValue() {

		@Override
		public BigDecimal getValue(EndOfDayData data) {
			return data.getStockPriceAdjClose();
		}
	};
	EndOfDayDataScanOnValue high = new EndOfDayDataScanOnValue() {

		@Override
		public BigDecimal getValue(EndOfDayData data) {
			return data.getStockPriceHigh();
		}
	};
	EndOfDayDataScanOnValue low = new EndOfDayDataScanOnValue() {
		@Override
		public BigDecimal getValue(EndOfDayData data) {
			return data.getStockPriceLow();
		}
	};
	EndOfDayDataScanOnValue open = new EndOfDayDataScanOnValue() {
		@Override
		public BigDecimal getValue(EndOfDayData data) {
			return data.getStockPriceOpen();
		}
	};

	public enum SCAN_ON {
		VOLUME, CLOSING, HIGH, LOW, OPEN
	}

	TechnicalIndicator_[] calculate(List<EndOfDayData> historical,
			Expression exp);

}
