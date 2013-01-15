package gabriel.yuppiewall.market.domain;

import gabriel.yuppiewall.common.meta.FieldDef;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Instrument implements Serializable {

	@FieldDef(field = "Instrument", notnull = true)
	private String name;
	private Exchange exchange;

	private Date startDate;
	private Date endDate;

	public Instrument() {
	}

	public Instrument(String name, Exchange exchange, Date startDate,
			Date endDate) {
		this.name = name;
		this.exchange = exchange;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public Exchange getExchange() {
		return exchange;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

}
