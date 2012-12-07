package gabriel.yuppiewall.market.domain;

import java.util.Date;

public class TradeDay_ {

	public static final String DATE_PATTERN = "ddMMyyyy";

	private Exchange_ exchange;
	private Date date;
	private int businessday;

	public Exchange_ getExchange() {
		return exchange;
	}

	public TradeDay_(Exchange_ exchange, Date date, int businessday) {
		this.exchange = exchange;
		this.date = date;
		this.businessday = businessday;
	}

	public Date getDate() {
		return date;
	}

	public int getBusinessday() {
		return businessday;
	}

}
