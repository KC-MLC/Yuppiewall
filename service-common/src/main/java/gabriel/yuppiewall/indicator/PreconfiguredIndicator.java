package gabriel.yuppiewall.indicator;

import gabriel.yuppiewall.scanner.domain.Condition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PreconfiguredIndicator implements Serializable {

	private String name;
	private List<Condition> conditions = new ArrayList<Condition>();

	public PreconfiguredIndicator() {
	}

	public PreconfiguredIndicator(String name) {
		this.name = name;
	}

	public void addCondition(Condition condition) {
		conditions.add(condition);
	}

	public String getName() {
		return name;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

}
