package gabriel.yuppiewall.trade.domain;

import gabriel.yuppiewall.instrument.domain.Instrument;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class Order implements Serializable {

	public enum OrderType {
		MARKET(1), LIMIT(2), STOP(3), STOP_LIMIT(4);
		int code;

		OrderType(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public static OrderType getType(int c) {
			switch (c) {
			case 1:
				return MARKET;
			case 2:
				return LIMIT;
			case 3:
				return STOP;
			case 4:
				return STOP_LIMIT;
			default:
				throw new IllegalArgumentException("Invalid order type");

			}
		}
	}

	public enum TransactionType {
		BUY(1), SELL(2), BUY_TO_COVER(3), SELL_SHORT(4);

		int code;

		TransactionType(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public static TransactionType getType(int c) {
			switch (c) {
			case 1:
				return BUY;
			case 2:
				return SELL;
			case 3:
				return BUY_TO_COVER;
			case 4:
				return SELL_SHORT;
			default:
				throw new IllegalArgumentException("Invalid transaction type");

			}
		}

	}

	//private OrderType orderType;
	private TransactionType transactionType;
	private Date date;
	private Long quantity;
	private BigDecimal price;
	//private BigDecimal stopPrice;
	//private Date expire;
	private Account account;
	private Instrument instrument;

	public Order(TransactionType transactionType, Date date, Long quantity,
			BigDecimal price, Account account, Instrument instrument) {
		super();
		this.transactionType = transactionType;
		this.date = date;
		this.quantity = quantity;
		this.price = price;
		this.account = account;
		this.instrument = instrument;
	}

	public Order() {
	}

	public Date getDate() {
		return date;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public Long getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

}
