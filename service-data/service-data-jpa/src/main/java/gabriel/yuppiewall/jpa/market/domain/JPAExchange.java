package gabriel.yuppiewall.jpa.market.domain;

import java.io.Serializable;

import gabriel.yuppiewall.market.domain.Exchange_;

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

	public JPAExchange() {

	}

	public JPAExchange(Exchange_ e) {
		this.name = e.getName();
	}

	public Exchange_ getExchange() {
		return new Exchange_(name);
	}

}
