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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenaricInstrument other = (GenaricInstrument) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

}
