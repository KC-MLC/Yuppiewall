package gabriel.yuppiewall.instrument.domain;

import gabriel.yuppiewall.common.meta.FieldDef;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Instrument implements Serializable {

	@FieldDef(field = "Instrument", notnull = true)
	private String symbol;
	private Exchange exchange;

	// Property
	private String name;
	private String secter;
	// Region server
	private String server;
	private String industry;

	public enum InstrumentType {
		STOCK(1);
		private int code;

		private InstrumentType(int code) {
			this.code = code;
		}

		public Integer getCode() {
			return code;
		}

		public InstrumentType getType(int code) {

			switch (code) {
			case 1:
				return STOCK;
			default:
				throw new UnsupportedOperationException(
						"InstrumentType Not supported");
			}
		}
	}

	private List<EndOfDayData> eodList;

	private InstrumentType type = InstrumentType.STOCK;

	/*
	 * private Date startDate; private Date endDate;
	 */

	public Instrument() {
	}

	public Instrument(String symbol, Exchange exchange, String name,
			String secter, String server, String industry) {
		this.symbol = symbol;
		this.exchange = exchange;
		this.name = name;
		this.secter = secter;
		this.server = server;
		this.industry = industry;
	}

	public Instrument(String symbol, Exchange exchange, String name) {
		super();
		this.symbol = symbol;
		this.exchange = exchange;
		this.name = name;
	}

	public Instrument(String symbol, Exchange exchange) {
		this.symbol = symbol;
		this.exchange = exchange;
	}

	public Instrument(String symbol) {
		this.symbol = symbol;
	}

	public Instrument(Instrument instrument) {
		this.symbol = instrument.getSymbol();
		this.exchange = instrument.getExchange();
		this.name = instrument.getName();
		this.secter = instrument.getSecter();
		this.industry = instrument.getIndustry();
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Exchange getExchange() {
		return exchange;
	}

	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecter() {
		return secter;
	}

	public void setSecter(String secter) {
		this.secter = secter;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public InstrumentType getType() {
		return type;
	}

	public void setType(InstrumentType type) {
		this.type = type;
	}

	public List<EndOfDayData> getEodList() {
		return eodList;
	}

	public void setEodList(List<EndOfDayData> eodList) {
		this.eodList = eodList;
	}

	@Override
	public String toString() {
		return "Instrument [symbol=" + symbol + ", exchange=" + exchange
				+ ", name=" + name + ", secter=" + secter + ", server="
				+ server + ", industry=" + industry + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instrument other = (Instrument) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

}
