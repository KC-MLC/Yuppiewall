package gabriel.yuppiewall.scanner.domain;

import gabriel.yuppiewall.indicator.TechnicalIndicator;
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
	private String id;

	private TechnicalIndicator technicalIndicator;

	public Expression() {
	}

	public Expression(String parameters) {
		this.parameters = parameters;
	}

	public Expression(String indicator, PERIOD period, SCAN_ON scanOn,
			Integer offset) {
		this.indicator = indicator;
		this.period = period;
		this.scanOn = scanOn;
		this.offset = offset;
	}

	public Expression(String id, String indicator, SCAN_ON scanOn) {
		this.indicator = indicator;
		this.scanOn = scanOn;
		this.id = id;

	}

	public Expression(String id, String indicator, String parameters,
			Integer offset, PERIOD period, SCAN_ON scanOn) {
		this.indicator = indicator;
		this.period = period;
		this.scanOn = scanOn;
		this.parameters = parameters;
		this.offset = offset;
		this.id = id;
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

	public String getId() {
		return id;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public TechnicalIndicator getTechnicalIndicator() {
		return technicalIndicator;
	}

	public void setTechnicalIndicator(TechnicalIndicator technicalIndicator) {
		this.technicalIndicator = technicalIndicator;
	}

}
