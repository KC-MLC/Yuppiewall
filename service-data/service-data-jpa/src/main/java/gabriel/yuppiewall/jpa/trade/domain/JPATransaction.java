package gabriel.yuppiewall.jpa.trade.domain;

import gabriel.yuppiewall.jpa.um.domain.JPAPrincipal;
import gabriel.yuppiewall.trade.domain.Transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private JPAPrincipal user;

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
		user = new JPAPrincipal(transaction.getUser());
		symbol = transaction.getInstrument().getSymbol();
		dateOfExecution = transaction.getDateOfExecution();
		price = transaction.getPrice();
		quantity = transaction.getQuantity();
		txType = transaction.getType().getCode();
	}
}
