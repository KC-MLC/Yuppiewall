package gabriel.yuppiewall.scanner.domain;

import java.io.Serializable;

import gabriel.yuppiewall.common.Tupple;

@SuppressWarnings("serial")
public class GlobalFilter implements Serializable {

	private Tupple<String, String> group;

	private Condition avgVolue;
	private Condition avgPrice;

	public Tupple<String, String> getGroup() {
		return group;
	}

	public Condition getAvgVolue() {
		return avgVolue;
	}

	public Condition getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(Condition avgPrice) {
		this.avgPrice = avgPrice;
	}

	public void setGroup(Tupple<String, String> group) {
		this.group = group;
	}

	public void setAvgVolue(Condition avgVolue) {
		this.avgVolue = avgVolue;
	}

}
