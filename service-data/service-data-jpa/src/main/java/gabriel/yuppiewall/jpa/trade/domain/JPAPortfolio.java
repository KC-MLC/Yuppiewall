package gabriel.yuppiewall.jpa.trade.domain;

import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Portfolio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "portfolio")
public class JPAPortfolio implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "portfolio_id")
	private Long portfolioId;

	@Column(name = "portfolio_name", nullable = false)
	private String portfolioName;
	@Column(name = "creation_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationtDate;

	@OneToOne
	@JoinColumn(name = "account_id")
	private JPAAccount account;

	public static final String REL_HAS_INSTRUMENT = "has_instument";

	public JPAPortfolio() {
	}

	public JPAPortfolio(Portfolio portfolio) {
		this.portfolioName = portfolio.getPortfolioName();
		this.creationtDate = portfolio.getCreationtDate();
		String value = portfolio.getPortfolioId();
		if (value != null && !(value = value.trim()).isEmpty())
			this.portfolioId = Long.parseLong(value);
		this.account = new JPAAccount(portfolio.getAccount());
	}

	public Long getPortfolioId() {
		return portfolioId;
	}

	public Portfolio getPortfolio() {
		return new Portfolio(account.getAccount(), portfolioId.toString(),
				portfolioName, creationtDate);
	}

	public Portfolio getPortfolio(Account account) {
		return new Portfolio(account, portfolioId.toString(), portfolioName,
				creationtDate);
	}

}
