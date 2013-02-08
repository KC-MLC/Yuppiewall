package gabriel.yuppiewall.ds.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TechnicalIndicatorOutput implements
		Comparable<TechnicalIndicatorOutput> {

	private Date date;
	private String type;
	private String key;
	private BigDecimal value;

	public TechnicalIndicatorOutput(Date date, String type, String key,
			BigDecimal value) {
		super();
		this.date = date;
		this.type = type;
		this.key = key;
		this.value = value;
	}

	public String getType() {
		return type;
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

	@Override
	public int compareTo(TechnicalIndicatorOutput o) {
		return value.compareTo(o.value);
	}

}
