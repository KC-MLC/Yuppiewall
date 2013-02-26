package gabriel.yuppiewall.jpa.trade.domain;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Order.TransactionType;
import gabriel.yuppiewall.trade.domain.Transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_transaction")
public class JPATransaction implements Serializable {

	@Id
	@Column(name = "transaction_id")
	private String transactionId;

	@OneToOne
	@JoinColumn(name = "account_id")
	private JPAAccount account;

	@Column(name = "tx_type", insertable = true, nullable = false, updatable = false)
	private Integer txType;
	@Column(name = "symbol", insertable = true, nullable = false, updatable = false)
	private String symbol;
	@Column(name = "date_of_execution", insertable = true, nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfExecution;
	@Column(name = "price", insertable = true, nullable = false, updatable = false)
	private BigDecimal price;
	@Column(name = "quantity", insertable = true, nullable = false, updatable = false)
	private Long quantity;

	public JPATransaction() {
	}

	public JPATransaction(Transaction transaction) {
		transactionId = transaction.getTransactionId();
		account = new JPAAccount(transaction.getAccount());
		symbol = transaction.getInstrument().getSymbol();
		dateOfExecution = transaction.getDateOfExecution();
		price = transaction.getPrice();
		quantity = transaction.getQuantity();
		txType = transaction.getType().getCode();
	}

	public Transaction getTransaction(Account account) {

		return new Transaction(transactionId, account,
				TransactionType.getType(txType), new Instrument(symbol),
				dateOfExecution, price, quantity);
	}

	public Transaction getTransaction() {

		return new Transaction(transactionId, account.getAccount(),
				TransactionType.getType(txType), new Instrument(symbol),
				dateOfExecution, price, quantity);
	}
}
