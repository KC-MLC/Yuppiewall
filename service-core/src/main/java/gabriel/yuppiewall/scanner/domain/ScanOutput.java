package gabriel.yuppiewall.scanner.domain;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.math.BigDecimal;

public class ScanOutput {

	private Instrument instrument;

	private EndOfDayData eod;

	public ScanOutput(Instrument instrument, EndOfDayData eod) {
		this.instrument = instrument;
		this.eod = eod;
	}

	public String getSymbol() {
		return instrument.getSymbol();
	}

	public String getName() {
		return instrument.getName();
	}

	public String getExchange() {
		return instrument.getExchange().getName();
	}

	public String getSector() {
		return instrument.getSecter();
	}

	public String getIndustry() {
		return instrument.getIndustry();
	}

	public BigDecimal getOpen() {
		return eod.getStockPriceOpen();
	}

	public BigDecimal getHigh() {
		return eod.getStockPriceHigh();
	}

	public BigDecimal getLow() {
		return eod.getStockPriceLow();
	}

	public BigDecimal getClose() {
		return eod.getStockPriceClose();
	}

	public BigDecimal getVolume() {
		return eod.getStockVolume();
	}
}
