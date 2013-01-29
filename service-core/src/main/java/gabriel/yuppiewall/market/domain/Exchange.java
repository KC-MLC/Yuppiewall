package gabriel.yuppiewall.market.domain;

import gabriel.yuppiewall.common.meta.FieldDef;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Exchange implements Serializable {

	@FieldDef(field = "Exchange", notnull = true)
	private String name;
	private String symbol;

	private String country;

	private String timeZone;

	public Exchange(String name, String symbol, String country, String timeZone) {
		super();
		this.name = name;
		this.symbol = symbol;
		this.country = country;
		this.timeZone = timeZone;
	}

	public Exchange() {
	}

	public Exchange(String symbol) {
		this.symbol = symbol;
	}

	public String getName1() {
		return name;
	}

	public void setName1(String name) {
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
		return "Exchange_ [name=" + name + "]";
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

}
