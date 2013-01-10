package gabriel.yuppiewall.trade.domain;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Order.TransactionType;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class Transaction implements Serializable {

	private String transactionId;
	private PrimaryPrincipal user;
	private TransactionType type;

	private Instrument instrument;
	private Date dateOfExecution;

	private BigDecimal price;
	private Long quantity;

	public Transaction() {
	}

	public Transaction(String transactionId, PrimaryPrincipal user,
			TransactionType type, Instrument instrument, Date dateOfExecution,
			BigDecimal price, Long quantity) {

		this.transactionId = transactionId;
		this.type = type;
		this.instrument = instrument;
		this.dateOfExecution = dateOfExecution;
		this.price = price;
		this.quantity = quantity;
		this.user = user;
	}

	public PrimaryPrincipal getUser() {
		return user;
	}

	public TransactionType getType() {
		return type;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public Date getDateOfExecution() {
		return dateOfExecution;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public String getTransactionId() {
		return transactionId;
	}

}
