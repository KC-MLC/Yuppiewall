package gabriel.yuppiewall.market.domain;

import gabriel.yuppiewall.common.meta.FieldDef;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Exchange_ implements Serializable {

	@FieldDef(field = "Exchange", notnull = true)
	private String name;

	public Exchange_() {
	}

	public Exchange_(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Exchange_ [name=" + name + "]";
	}

}
