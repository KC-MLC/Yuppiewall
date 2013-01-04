package gabriel.yuppiewall.scanner.domain;

import gabriel.yuppiewall.scanner.domain.ScanParameter.PERIOD;
import gabriel.yuppiewall.scanner.domain.ScanParameter.SCAN_ON;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class Expression implements Serializable {

	private String indicator;
	private PERIOD period;
	private SCAN_ON scanOn;
	private BigDecimal value;

	public Expression() {
	}

	public Expression(String indicator, PERIOD period, BigDecimal value,
			SCAN_ON scanOn) {
		this.indicator = indicator;
		this.period = period;
		this.scanOn = scanOn;
		this.value = value;

	}

	public Expression(BigDecimal value) {
		this.value = value;
	}

	public String getIndicator() {
		return indicator;
	}

	public SCAN_ON getScanOn() {
		return scanOn;
	}

	public BigDecimal getValue() {
		return this.value;
	}

	public PERIOD getPeriod() {
		return period;
	}

}
