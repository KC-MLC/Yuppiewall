package gabriel.yuppiewall.marketdata.domain;

import gabriel.yuppiewall.instrument.domain.Instrument;

import java.io.Serializable;

@SuppressWarnings("serial")
public class InstrumentMarketDetail implements Serializable {

	private Instrument instrument;

	private Long lastPrice;
	private String quoteChange;
	private String marketCap;

	public InstrumentMarketDetail() {
	}

	public InstrumentMarketDetail(Instrument instrument, Long lastPrice) {
		this.instrument = instrument;
		this.lastPrice = lastPrice;
	}

}
