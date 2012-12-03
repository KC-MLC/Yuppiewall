package gabriel.yuppiewall.market.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Exchange_ implements Serializable {

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
