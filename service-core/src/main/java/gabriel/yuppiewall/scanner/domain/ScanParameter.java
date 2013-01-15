package gabriel.yuppiewall.scanner.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ScanParameter implements Serializable {

	private String scanId;

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

	public GlobalFilter getGlobalFilter() {
		return globalFilter;
	}
}
