package gabriel.yuppiewall.market.domain;

import gabriel.yuppiewall.common.meta.FieldDef;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Exchange_ implements Serializable {

	@FieldDef(field = "Exchange", notnull = true)
	private String name;

	private String country;

	public Exchange_() {
	}

	public Exchange_(String name) {
		this.name = name;

	}

	public Exchange_(String name, String country) {
		this.name = name;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Exchange_ [name=" + name + "]";
	}

}
