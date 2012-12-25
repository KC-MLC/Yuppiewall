package gabriel.yuppiewall.scanner.domain;

import gabriel.yuppiewall.scanner.domain.ScanParameter.OPERAND;
import gabriel.yuppiewall.scanner.domain.ScanParameter.PERIOD;
import gabriel.yuppiewall.scanner.domain.ScanParameter.SCAN_ON;

public class Condition {

	private String indicator;
	private PERIOD period;
	private SCAN_ON scanOn;
	private int value;
	private OPERAND operand;
	private Condition rhs;

	public Condition(String indicator, PERIOD period, int value, SCAN_ON scanOn) {
		this.indicator = indicator;
		this.period = period;
		this.scanOn = scanOn;
		this.value = value;

	}

	public Condition(int value) {
		this.value = value;
	}

	public String getIndicator() {
		return indicator;
	}

	public Condition greterThen(int i) {
		operand = OPERAND.GT;
		rhs = new Condition(i);
		return this;
	}

	public Condition getLHS() {
		return new Condition(indicator, period, value, scanOn);
	}

	public Condition getRHS() {
		return rhs;
	}

	public OPERAND getOperand() {
		return operand;
	}

	public SCAN_ON getScanOn() {
		return scanOn;
	}

	public int getValue() {
		return this.value;
	}

}
