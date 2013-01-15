package gabriel.yuppiewall.jpa.market.domain;

import gabriel.yuppiewall.market.domain.TradeDay;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Table(name = "trade_day")
/*
 * @NamedQueries({
 * 
 * @NamedQuery(name = "JPATradeDay.businessday", query =
 * "UPDATE JPATradeDay td SET td.businessday = td.businessday + 1 WHERE td.date > :date AND td.exchange= :exchange)"
 * ),
 * 
 * @NamedQuery(name = "JPATradeDay.findLastTradeDayByExchange", query =
 * "select e from JPATradeDay e where e.date =(select max(e1.date) from JPATradeDay e1 where e1.exchange=:exchange) and e.exchange = :exchange"
 * ),
 * 
 * @NamedQuery(name = "JPATradeDay.findLastTradeDayBy", query =
 * "select e from JPATradeDay e where e.date =(select max(e1.date) from JPATradeDay e1 where e1.exchange=:exchange and e1.date < :date) and e.exchange = :exchange"
 * ) })
 */
public class JPATradeDay implements Serializable {

	@Id
	private String identifier;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "exchange", nullable = false, updatable = false)
	private JPAExchange exchange;

	@Column(name = "trade_date")
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name = "business_day", nullable = false, updatable = false)
	private int businessday;

	public JPATradeDay() {

	}

	public JPATradeDay(TradeDay td) {
		this.identifier = td.getExchange().getName()
				+ ":"
				+ new SimpleDateFormat(TradeDay.DATE_PATTERN).format(td
						.getDate());
		this.exchange = new JPAExchange(td.getExchange());
		this.date = td.getDate();
		this.businessday = td.getBusinessday();

	}

	public JPAExchange getExchange() {
		return exchange;
	}

	public Date getDate() {
		return date;
	}

	public int getBusinessday() {
		return businessday;
	}

	public TradeDay getTradeDay() {
		return new TradeDay(exchange.getExchange(), this.date,
				this.businessday);
	}
}
