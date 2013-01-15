package gabriel.yuppiewall.scanner.domain;

import gabriel.yuppiewall.scanner.domain.ScanParameter.OPERAND;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Condition implements Serializable {

	private Expression lhs;
	private OPERAND operand;
	private Expression rhs;

	public Condition() {
	}

	public Condition(Expression lhs, OPERAND operand, Expression rhs) {
		this.lhs = lhs;
		this.operand = operand;
		this.rhs = rhs;
	}

	public OPERAND getOperand() {
		return operand;
	}

	public Expression getLhs() {
		return lhs;
	}

	public Expression getRhs() {
		return rhs;
	}

}
