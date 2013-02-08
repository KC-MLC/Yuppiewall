package gabriel.yuppiewall.scanner.domain;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ScanOutput implements Serializable {

	private EndOfDayData eod;
	private Instrument instrument;

	public ScanOutput() {
	}

	public ScanOutput(EndOfDayData eod) {
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
