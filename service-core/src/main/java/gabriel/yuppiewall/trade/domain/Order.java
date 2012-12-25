package gabriel.yuppiewall.trade.domain;

import gabriel.yuppiewall.instrument.domain.Security;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class Order implements Serializable {

	public enum OrderType {
		MARKET, LIMIT, STOP, STOP_LIMIT
	}

	private OrderType orderType;
	private Date date;
	private Long quantity;
	private BigDecimal price;
	private BigDecimal stopPrice;
	private Date expire;
	private Account account;
	private Security security;

}
