package gabriel.yuppiewall.scanner.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gabriel.yuppiewall.market.domain.Exchange_;

public class ScanParameter {

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

	static class Builder {

		private ScanParameter param = new ScanParameter();

		public Builder() {
			param.conditions = new ArrayList<>();

		}

		public Builder fromExchange(Exchange_ exchange) {
			param.setExchange(exchange);
			return this;
		}

		Builder addCondition(Condition condition) {
			param.conditions.add(condition);
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
		ScanParameter sp = new ScanParameter()
				.Builder()
				.fromExchange(new Exchange_("NYSE"))
				.addCondition(
						new Condition("SMA", PERIOD.DAILY, 20, SCAN_ON.VOLUME)
								.greterThen(40000)).build();
	}

	private Builder Builder() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Condition> getCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
