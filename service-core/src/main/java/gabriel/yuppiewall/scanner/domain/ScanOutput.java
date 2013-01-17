package gabriel.yuppiewall.scanner.domain;

import java.math.BigDecimal;

public class ScanOutput {

	private String symbol;
	private String name;
	private String exchange;
	private String sector;
	private String industry;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal close;
	private BigDecimal volume;

	public ScanOutput(String symbol, String name, String exchange,
			String sector, String industry, BigDecimal open, BigDecimal high,
			BigDecimal low, BigDecimal close, BigDecimal volume) {
		this.symbol = symbol;
		this.name = name;
		this.exchange = exchange;
		this.sector = sector;
		this.industry = industry;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

	public String getExchange() {
		return exchange;
	}

	public String getSector() {
		return sector;
	}

	public String getIndustry() {
		return industry;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public BigDecimal getClose() {
		return close;
	}

	public BigDecimal getVolume() {
		return volume;
	}

}
