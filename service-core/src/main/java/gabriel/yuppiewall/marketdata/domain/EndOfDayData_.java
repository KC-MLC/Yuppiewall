package gabriel.yuppiewall.marketdata.domain;

import gabriel.yuppiewall.market.domain.Exchange_;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class EndOfDayData_ implements Serializable {

	private Exchange_ exchange;
	private String stockSymbol;
	private Date date;
	private String strDate;
	private BigInteger stockVolume;
	private BigDecimal stockPriceOpen, stockPriceHigh, stockPriceLow,
			stockPriceClose, stockPriceAdjClose;

	public EndOfDayData_() {
	}

	public EndOfDayData_(Exchange_ exchange, String stockSymbol, Date date,
			BigDecimal stockPriceOpen, BigDecimal stockPriceHigh,
			BigDecimal stockPriceLow, BigDecimal stockPriceClose,
			BigInteger stockVolume, BigDecimal stockPriceAdjClose) {

		this.exchange = exchange;
		this.stockSymbol = stockSymbol;
		this.date = date;
		this.strDate = new SimpleDateFormat("yyyymmdd").format(date);
		this.stockPriceOpen = stockPriceOpen;
		this.stockPriceHigh = stockPriceHigh;
		this.stockPriceLow = stockPriceLow;
		this.stockPriceClose = stockPriceClose;
		this.stockVolume = stockVolume;
		this.stockPriceAdjClose = stockPriceAdjClose;
	}

	public Exchange_ getExchange() {
		return exchange;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public Date getDate() {
		return date;
	}

	public BigDecimal getStockPriceOpen() {
		return stockPriceOpen;
	}

	public BigDecimal getStockPriceHigh() {
		return stockPriceHigh;
	}

	public BigDecimal getStockPriceLow() {
		return stockPriceLow;
	}

	public BigDecimal getStockPriceClose() {
		return stockPriceClose;
	}

	public BigInteger getStockVolume() {
		return stockVolume;
	}

	public BigDecimal getStockPriceAdjClose() {
		return stockPriceAdjClose;
	}

	public String getStrDate() {
		return strDate;
	}

}
