package gabriel.yuppiewall.jpa.market.domain;

import gabriel.yuppiewall.instrument.domain.Instrument;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "instrument")
public class JPAInstrument implements Serializable {

	@Id
	@Column(name = "symbol_code")
	private String symbol;

	@Column(name = "comp_name")
	private String name;
	@Column(name = "industry")
	private String industry;

	@Column(name = "sector")
	private String sector;

	@Column(name = "server_id")
	private String server;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "exchange", nullable = false, updatable = false)
	private JPAExchange exchange;

	public JPAInstrument() {

	}

	public JPAInstrument(Instrument e) {
		this.symbol = e.getSymbol();
		this.industry = e.getIndustry();
		this.name = e.getName();
		this.sector = e.getSecter();

		this.exchange = new JPAExchange(e.getExchange());
	}

	public JPAInstrument(String symbol) {
		this.symbol = symbol;
	}

	public Instrument getInstrument() {
		return new Instrument(symbol, exchange.getExchange(), name, sector,
				server, industry);
	}
}
