package gabriel.yuppiewall.jpa.market.domain;

import gabriel.yuppiewall.market.domain.TradeDay_;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "exchange")
@NamedQuery(name = "JPATradeDay.findLastTradeDayByExchange", query = "select max(e.date) from JPATradeDay e where e.exchange=?")
public class JPATradeDay implements Serializable {

	@Id
	private String identifier;

	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "exchange", nullable = false)
	private JPAExchange exchange;

	@Column(name = "trade_date")
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name = "business_day", nullable = false, updatable = false)
	private int businessday;

	public JPATradeDay() {

	}

	public JPATradeDay(TradeDay_ td) {
		this.identifier = td.getExchange()
				+ new SimpleDateFormat("ddmmyyyy").format(td.getDate());
		this.exchange = new JPAExchange(td.getExchange());
		this.date = td.getDate();
		this.businessday = td.getBusinessday();

	}

	public TradeDay_ getTradeDay() {
		return new TradeDay_(exchange.getExchange(), this.date,
				this.businessday);
	}
}
