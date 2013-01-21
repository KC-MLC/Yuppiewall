package gabriel.yuppiewall.marketdata.domain;

import gabriel.yuppiewall.instrument.domain.Instrument;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class EndOfDayData implements Serializable {

	private Instrument instrument;
	public static final String DATE_FORMAT = "ddMMyyyy";
	private Date date;
	private String strDate;
	private BigDecimal stockVolume;
	private BigDecimal stockPriceOpen, stockPriceHigh, stockPriceLow,
			stockPriceClose, stockPriceAdjClose;

	public EndOfDayData() {
	}

	public EndOfDayData(Instrument instrument, Date date,
			BigDecimal stockPriceOpen, BigDecimal stockPriceHigh,
			BigDecimal stockPriceLow, BigDecimal stockPriceClose,
			BigDecimal stockVolume, BigDecimal stockPriceAdjClose) {

		this.instrument = instrument;
		this.date = date;
		this.strDate = new SimpleDateFormat(DATE_FORMAT).format(date);
		this.stockPriceOpen = stockPriceOpen;
		this.stockPriceHigh = stockPriceHigh;
		this.stockPriceLow = stockPriceLow;
		this.stockPriceClose = stockPriceClose;
		this.stockVolume = stockVolume;
		this.stockPriceAdjClose = stockPriceAdjClose;
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

	public String getStrDate() {
		return strDate;
	}

	public Instrument getInstrument() {
		return instrument;
	}

}
