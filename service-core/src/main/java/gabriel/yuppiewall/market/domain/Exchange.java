package gabriel.yuppiewall.market.domain;

import gabriel.yuppiewall.common.meta.FieldDef;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Exchange implements Serializable {

	@FieldDef(field = "Exchange", notnull = true)
	private String name;

	private String country;

	public Exchange() {
	}

	public Exchange(String name) {
		this.name = name;

	}

	public Exchange(String name, String country) {
		this.name = name;
		this.country = country;
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

	@Override
	public String toString() {
		return "Exchange_ [name=" + name + "]";
	}

}
