package gabriel.yuppiewall.gwt.client.application.portfolio;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;

import gabriel.yuppiewall.instrument.domain.Instrument;

public class InstrumentMultiWordSuggestion extends MultiWordSuggestion {
	private Instrument instrument;

	public InstrumentMultiWordSuggestion(Instrument instrument) {
		super(instrument.getName() + " " + instrument.getSymbol(), instrument
				.getName() + " " + instrument.getSymbol());

		this.instrument = instrument;
	}

	public Instrument getInstrument() {
		return instrument;
	}
}