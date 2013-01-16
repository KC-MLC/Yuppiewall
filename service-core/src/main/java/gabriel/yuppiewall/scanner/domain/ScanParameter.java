package gabriel.yuppiewall.scanner.domain;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ScanParameter implements Serializable {

	private String scanId;
	private List<Condition> conditions;

	public enum PERIOD {
		DAYS, WEEK
	}

	public enum OPERAND {
		GT(">"), LT("<"), EQUAL("=");

		private String symbol;

		private OPERAND(String symbol) {
			this.symbol = symbol;
		}

		public String getSymbol() {
			return symbol;
		}
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
