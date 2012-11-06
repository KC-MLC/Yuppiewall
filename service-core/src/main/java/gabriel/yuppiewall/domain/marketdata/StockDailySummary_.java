package gabriel.yuppiewall.domain.marketdata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class StockDailySummary_ implements Serializable {

	private String exchange, stockSymbol;
	private Date date;
	private BigDecimal stockPriceOpen, stockPriceHigh, stockPriceLow,
			stockPriceClose, stockVolume, stockPriceAdjClose;

	public StockDailySummary_() {
	}

	public StockDailySummary_(String exchange, String stockSymbol, Date date,
			BigDecimal stockPriceOpen, BigDecimal stockPriceHigh, BigDecimal stockPriceLow,
			BigDecimal stockPriceClose, BigDecimal stockVolume, BigDecimal stockPriceAdjClose) {

		this.exchange = exchange;
		this.stockSymbol = stockSymbol;
		this.date = date;
		this.stockPriceOpen = stockPriceOpen;
		this.stockPriceHigh = stockPriceHigh;
		this.stockPriceLow = stockPriceLow;
		this.stockPriceClose = stockPriceClose;
		this.stockVolume = stockVolume;
		this.stockPriceAdjClose = stockPriceAdjClose;
	}

	public String getExchange() {
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

	public BigDecimal getStockVolume() {
		return stockVolume;
	}

	public BigDecimal getStockPriceAdjClose() {
		return stockPriceAdjClose;
	}

}
