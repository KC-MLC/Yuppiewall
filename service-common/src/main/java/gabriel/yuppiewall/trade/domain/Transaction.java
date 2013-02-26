package gabriel.yuppiewall.trade.domain;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Order.TransactionType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class Transaction implements Serializable {

	private String transactionId;
	private Account account;
	private TransactionType type;

	private Instrument instrument;
	private Date dateOfExecution;

	private BigDecimal price;
	private Long quantity;

	public Transaction() {
	}

	public Transaction(String transactionId, Account account,
			TransactionType type, Instrument instrument, Date dateOfExecution,
			BigDecimal price, Long quantity) {

		this.transactionId = transactionId;
		this.type = type;
		this.instrument = instrument;
		this.dateOfExecution = dateOfExecution;
		this.price = price;
		this.quantity = quantity;
		this.account = account;
	}

	public Transaction(String transactionId, Instrument instrument) {

		this.transactionId = transactionId;
		this.instrument = instrument;
	}

	public Account getAccount() {
		return account;
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

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

}
