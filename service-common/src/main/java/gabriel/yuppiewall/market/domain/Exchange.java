package gabriel.yuppiewall.market.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Exchange implements Serializable {

	private String name;
	private String symbol;
	private String country;
	private String tradeCurrencyCode;

	private String timeZone;
	private String marketCloseSchedule;

	public Exchange(String name, String symbol, String country,
			String tradeCurrencyCode, String timeZone,
			String marketCloseSchedule) {
		super();
		this.name = name;
		this.symbol = symbol;
		this.country = country;
		this.tradeCurrencyCode = tradeCurrencyCode;
		this.timeZone = timeZone;
		this.marketCloseSchedule = marketCloseSchedule;
	}

	public Exchange() {
	}

	public Exchange(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return "Exchange_ [symbol=" + symbol + "]";
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getTradeCurrencyCode() {
		return tradeCurrencyCode;
	}

	public void setTradeCurrencyCode(String tradeCurrencyCode) {
		this.tradeCurrencyCode = tradeCurrencyCode;
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
		Exchange other = (Exchange) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

	public String getMarketCloseSchedule() {
		return marketCloseSchedule;
	}

	public void setMarketCloseSchedule(String marketCloseSchedule) {
		this.marketCloseSchedule = marketCloseSchedule;
	}

}
