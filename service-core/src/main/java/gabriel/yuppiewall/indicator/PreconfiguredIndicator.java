package gabriel.yuppiewall.indicator;

import gabriel.yuppiewall.scanner.domain.Condition;

import java.util.ArrayList;
import java.util.List;

public class PreconfiguredIndicator {

	private String name;
	private List<Condition> conditions = new ArrayList<>();

	public PreconfiguredIndicator(String name) {
		this.name = name;
	}

	public void addCondition(Condition condition) {
		conditions.add(condition);
	}

}
