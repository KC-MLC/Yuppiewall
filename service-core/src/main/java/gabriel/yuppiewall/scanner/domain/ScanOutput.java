package gabriel.yuppiewall.scanner.domain;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class ScanOutput implements Serializable {

	private Instrument instrument;

	private EndOfDayData eod;

	public ScanOutput() {
	}

	public ScanOutput(Instrument instrument, EndOfDayData eod) {
		this.instrument = instrument;
		this.eod = eod;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public EndOfDayData getEod() {
		return eod;
	}

	public void setEod(EndOfDayData eod) {
		this.eod = eod;
	}

}
