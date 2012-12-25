package gabriel.yuppiewall.jpa.market.domain;

import gabriel.yuppiewall.market.domain.Symbol;

import java.io.Serializable;
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
@Table(name = "symbol")
public class JPASymbol implements Serializable {

	@Id
	@Column(name = "symbol_code")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "exchange", nullable = false, updatable = false)
	private JPAExchange exchange;

	@Column(name = "start_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "end_date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date endDate;

	public JPASymbol() {

	}

	public JPASymbol(Symbol e) {
		this.name = e.getName();
		this.exchange = new JPAExchange(e.getExchange());
		this.startDate = e.getStartDate();
		this.endDate = e.getEndDate();
	}

	public Symbol getSymbol() {
		return new Symbol(name, exchange.getExchange(), startDate, endDate);
	}
}
