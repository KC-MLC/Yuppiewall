package gabriel.yuppiewall.scanner.domain;

import gabriel.yuppiewall.market.domain.Exchange_;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ScanParameter implements Serializable {

	private String scanId;
	private Exchange_ exchange;
	private Date fromDate;
	private Date toDate;
	private List<Condition> conditions;

	public Date getToDate() {
		return toDate;
	}

	public Exchange_ getExchange() {
		return exchange;
	}

	public String getScanId() {
		return scanId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public enum SCAN_ON {
		VOLUME, CLOSING
	}

	public enum PERIOD {
		DAILY, WEEKLY, MONTHLY, QUARTERLY
	}

	public enum OPERAND {
		GT, LT, EQUAL
	}

	public void setScanId(String uid) {
		this.scanId = uid;
	}

	public static class Builder implements Serializable {

		private ScanParameter param = new ScanParameter();

		public Builder() {
			param.conditions = new ArrayList<>();

		}

		public Builder fromExchange(Exchange_ exchange) {
			param.setExchange(exchange);
			return this;
		}

		public Builder addCondition(Expression lhs, OPERAND operand,
				Expression rhs) {
			param.conditions.add(new Condition(lhs, operand, rhs));
			return this;
		}

		public ScanParameter build() {
			return param;
		}
	}

	public void setExchange(Exchange_ exchange2) {
		this.exchange = exchange2;

	}

	public static void main(String[] args) {
		// [type = stock] and [country = us] and [daily sma(20,daily volume) >
		// 40000]
		/*
		 * ScanParameter sp = new ScanParameter() .Builder() .fromExchange(new
		 * Exchange_("NYSE")) .addCondition( new Expression("sma", PERIOD.DAILY,
		 * new BigDecimal(20), SCAN_ON.VOLUME), OPERAND.GT, new Expression(new
		 * BigDecimal(40000))).build();
		 */
	}

	public Builder Builder() {
		return new Builder();
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}
}
