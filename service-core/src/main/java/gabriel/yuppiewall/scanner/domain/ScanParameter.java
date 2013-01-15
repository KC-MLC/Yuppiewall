package gabriel.yuppiewall.scanner.domain;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ScanParameter implements Serializable {

	private String scanId;
	private List<Condition> conditions;

	public enum SCAN_ON {
		VOLUME, CLOSING
	}

	public enum PERIOD {
		DAILY, WEEKLY, MONTHLY, QUARTERLY
	}

	public enum OPERAND {
		GT, LT, EQUAL
	}

	private GlobalFilter globalFilter;

	public ScanParameter() {

	}

	public ScanParameter(GlobalFilter gf) {
		this.globalFilter = gf;
	}

	public GlobalFilter getGlobalFilter() {
		return globalFilter;
	}

	public List<Condition> getConditions() {
		return conditions;
	}
}
