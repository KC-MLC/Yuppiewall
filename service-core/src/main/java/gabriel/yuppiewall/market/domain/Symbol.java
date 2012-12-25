package gabriel.yuppiewall.market.domain;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Symbol implements Serializable {

	private String name;
	private Exchange_ exchange;

	private Date startDate;
	private Date endDate;

	public Symbol() {
	}

	public Symbol(String name, Exchange_ exchange, Date startDate, Date endDate) {
		this.name = name;
		this.exchange = exchange;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public Exchange_ getExchange() {
		return exchange;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

}
