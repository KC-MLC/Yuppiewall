package gabriel.yuppiewall.instrument.domain;

public class GenaricInstrument implements Instrument {

	private String symbol;

	public GenaricInstrument(String symbol) {
		this.symbol = symbol;
	}

	public GenaricInstrument() {
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

}
