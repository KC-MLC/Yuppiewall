package gabriel.yuppiewall.scanner.domain;

import gabriel.yuppiewall.indicator.TechnicalIndicator.SCAN_ON;
import gabriel.yuppiewall.scanner.domain.ScanParameter.PERIOD;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Expression implements Serializable {

	private String indicator;
	private PERIOD period;
	private SCAN_ON scanOn;
	private String parameters;
	private Integer offset;

	public Expression() {
	}

	public Expression(String indicator, String parameters, Integer offset,
			PERIOD period, SCAN_ON scanOn) {
		this.indicator = indicator;
		this.period = period;
		this.scanOn = scanOn;
		this.parameters = parameters;
		this.offset = offset;

	}

	public String getIndicator() {
		return indicator;
	}

	public SCAN_ON getScanOn() {
		return scanOn;
	}

	public PERIOD getPeriod() {
		return period;
	}

	public String getParameters() {
		return parameters;
	}

	public Integer getOffset() {
		return offset;
	}

}
