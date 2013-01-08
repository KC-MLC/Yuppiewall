package gabriel.yuppiewall.trade.domain;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Order.TransactionType;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class Transaction implements Serializable {
	private PrimaryPrincipal user;
	private TransactionType type;

	private Instrument instrument;
	private Date dateOfExecution;

	private BigDecimal price;
	private Long quantity;

	public Transaction(PrimaryPrincipal user, TransactionType type,
			Instrument instrument, Date dateOfExecution, BigDecimal price,
			Long quantity) {
		super();
		this.type = type;
		this.instrument = instrument;
		this.dateOfExecution = dateOfExecution;
		this.price = price;
		this.quantity = quantity;
		this.user = user;
	}

}
