package gabriel.yuppiewall.scanner.domain;

import java.io.Serializable;

import gabriel.yuppiewall.common.Tuple;

@SuppressWarnings("serial")
public class GlobalFilter implements Serializable {

	private Tuple<String, String> group;

	private Condition avgVolue;
	private Condition avgPrice;

	public Tuple<String, String> getGroup() {
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

	public void setGroup(Tuple<String, String> group) {
		this.group = group;
	}

	public void setAvgVolue(Condition avgVolue) {
		this.avgVolue = avgVolue;
	}

}
