package gabriel.yuppiewall.marketdata.repository;

import gabriel.yuppiewall.scanner.domain.Condition;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class ScanRequest implements Serializable {

	private Set<String /* symbol */> exchanges;
	private List<Condition> conditions;

	public ScanRequest() {
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public Set<String> getExchanges() {
		return exchanges;
	}

	public void setExchanges(Set<String> exchanges) {
		this.exchanges = exchanges;
	}

}
