package gabriel.yuppiewall.jpa.marketdata.domain;

import gabriel.yuppiewall.jpa.market.domain.JPAExchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;

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
@Table(name = "end_of_day_data")
public class JPAEndOfDayData implements Serializable {

	@Id
	private String identifier;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "exchange", nullable = false, updatable = false)
	private JPAExchange exchange;

	@Column(name = "symbol", nullable = false, updatable = false)
	private String symbol;

	@Column(name = "trade_date")
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name = "stock_volume", precision = 14, nullable = false, updatable = false)
	private BigDecimal stockVolume;

	@Column(name = "stock_price_open", precision = 14, scale = 2, nullable = false, updatable = false)
	private BigDecimal stockPriceOpen;

	@Column(name = "stock_price_high", precision = 14, nullable = false, updatable = false)
	private BigDecimal stockPriceHigh;
	@Column(name = "stock_price_low", precision = 14, nullable = false, updatable = false)
	private BigDecimal stockPriceLow;
	@Column(name = "stock_price_close", precision = 14, nullable = false, updatable = false)
	private BigDecimal stockPriceClose;
	@Column(name = "stock_price_adj_close", precision = 14, nullable = false, updatable = false)
	private BigDecimal stockPriceAdjClose;

	public JPAEndOfDayData() {

	}

	public JPAEndOfDayData(EndOfDayData_ eodd) {

		this.date = eodd.getDate();
		this.stockPriceAdjClose = eodd.getStockPriceAdjClose();
		this.stockPriceClose = eodd.getStockPriceClose();
		this.stockPriceHigh = eodd.getStockPriceHigh();
		this.stockPriceLow = eodd.getStockPriceLow();
		this.stockPriceOpen = eodd.getStockPriceOpen();
		this.stockVolume = eodd.getStockVolume();
		this.symbol = eodd.getStockSymbol();
		this.exchange = new JPAExchange(eodd.getExchange());

		this.identifier = eodd.getExchange().getName() + symbol
				+ eodd.getStrDate();
	}

	public EndOfDayData_ getEndOfDayData() {
		return new EndOfDayData_(exchange.getExchange(), symbol, date,
				stockPriceOpen, stockPriceHigh, stockPriceLow, stockPriceClose,
				stockVolume, stockPriceAdjClose);
	}
}