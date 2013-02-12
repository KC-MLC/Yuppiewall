package gabriel.yuppiewall.marketdata.domain;

import gabriel.yuppiewall.instrument.domain.Instrument;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class EndOfDayData implements Serializable {

	private Instrument instrument;
	public transient static final String DATE_FORMAT = "ddMMyyyy";
	private transient final SimpleDateFormat sdf = new SimpleDateFormat(
			DATE_FORMAT);
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
		this.strDate = sdf.format(date);
		this.stockPriceOpen = stockPriceOpen;
		this.stockPriceHigh = stockPriceHigh;
		this.stockPriceLow = stockPriceLow;
		this.stockPriceClose = stockPriceClose;
		this.stockVolume = stockVolume;
		this.stockPriceAdjClose = stockPriceAdjClose;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStrDate() {
		return strDate;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	public BigDecimal getStockVolume() {
		return stockVolume;
	}

	public void setStockVolume(BigDecimal stockVolume) {
		this.stockVolume = stockVolume;
	}

	public BigDecimal getStockPriceOpen() {
		return stockPriceOpen;
	}

	public void setStockPriceOpen(BigDecimal stockPriceOpen) {
		this.stockPriceOpen = stockPriceOpen;
	}

	public BigDecimal getStockPriceHigh() {
		return stockPriceHigh;
	}

	public void setStockPriceHigh(BigDecimal stockPriceHigh) {
		this.stockPriceHigh = stockPriceHigh;
	}

	public BigDecimal getStockPriceLow() {
		return stockPriceLow;
	}

	public void setStockPriceLow(BigDecimal stockPriceLow) {
		this.stockPriceLow = stockPriceLow;
	}

	public BigDecimal getStockPriceClose() {
		return stockPriceClose;
	}

	public void setStockPriceClose(BigDecimal stockPriceClose) {
		this.stockPriceClose = stockPriceClose;
	}

	public BigDecimal getStockPriceAdjClose() {
		return stockPriceAdjClose;
	}

	public void setStockPriceAdjClose(BigDecimal stockPriceAdjClose) {
		this.stockPriceAdjClose = stockPriceAdjClose;
	}

	@Override
	public String toString() {
		return "EndOfDayData [instrument=" + instrument + ", date=" + date
				+ ", strDate=" + strDate + ", stockVolume=" + stockVolume
				+ ", stockPriceOpen=" + stockPriceOpen + ", stockPriceHigh="
				+ stockPriceHigh + ", stockPriceLow=" + stockPriceLow
				+ ", stockPriceClose=" + stockPriceClose
				+ ", stockPriceAdjClose=" + stockPriceAdjClose + "]";
	}

}
