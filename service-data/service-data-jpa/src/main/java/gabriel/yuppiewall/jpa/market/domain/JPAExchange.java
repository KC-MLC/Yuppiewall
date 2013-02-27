package gabriel.yuppiewall.jpa.market.domain;

import gabriel.yuppiewall.market.domain.Exchange;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "exchange")
public class JPAExchange implements Serializable {

	@Id
	@Column(name = "ex_symbol")
	private String symbol;

	@Column(name = "ex_name")
	private String name;
	@Column(name = "trade_currency")
	private String tradeCurrency;

	@Column(name = "ex_country_code")
	private String country;

	@Column(name = "ex_time_zone")
	private String timeZone;
	@Column(name = "ex_close_schedule")
	private String marketCloseSchedule;

	public JPAExchange() {

	}

	public JPAExchange(Exchange e) {
		this.symbol = e.getSymbol();
		this.name = e.getName();
		this.country = e.getCountry();
		this.timeZone = e.getTimeZone();
		this.tradeCurrency = e.getTradeCurrencyCode();
		this.marketCloseSchedule = e.getMarketCloseSchedule();
	}

	public Exchange getExchange() {
		return new Exchange(name, symbol, country, tradeCurrency, timeZone,
				marketCloseSchedule);
	}

}
