package gabriel.yuppiewall.indicator.domain;

import java.math.BigDecimal;
import java.util.Date;

public class SimpleMovingAverage_ {

	private Date date;
	private String key;
	private BigDecimal value;

	public SimpleMovingAverage_(Date date, String key, BigDecimal value) {
		super();
		this.date = date;
		this.key = key;
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public String getKey() {
		return key;
	}

	public BigDecimal getValue() {
		return value;
	}

}
