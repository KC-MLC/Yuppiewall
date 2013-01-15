package gabriel.yuppiewall.jpa.market.domain;

import java.io.Serializable;

import gabriel.yuppiewall.market.domain.Exchange;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "exchange")
public class JPAExchange implements Serializable {

	@Id
	@Column(name = "ex_name")
	private String name;
	@Column(name = "ex_country_code")
	private String country;

	public JPAExchange() {

	}

	public JPAExchange(Exchange e) {
		this.name = e.getName();
		this.country = e.getCountry();
	}

	public Exchange getExchange() {
		return new Exchange(name, country);
	}

}
