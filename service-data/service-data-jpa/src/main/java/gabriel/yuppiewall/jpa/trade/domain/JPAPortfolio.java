package gabriel.yuppiewall.jpa.trade.domain;

import gabriel.yuppiewall.jpa.um.domain.JPAPrincipal;
import gabriel.yuppiewall.trade.domain.Portfolio;

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
@Table(name = "portfolio")
public class JPAPortfolio implements Serializable {

	@Id
	@Column(name = "portfolio_id")
	private String portfolioId;

	@Column(name = "portfolio_name", nullable = false)
	private String portfolioName;
	@Column(name = "creation_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationtDate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "owner_id", nullable = false, updatable = false)
	private JPAPrincipal ownerID;

	public JPAPortfolio() {
	}

	public JPAPortfolio(Portfolio portfolio) {
		this.portfolioName = portfolio.getPortfolioName();
		this.creationtDate = portfolio.getCreationtDate();
		this.portfolioId = portfolio.getPortfolioId();
		this.ownerID = new JPAPrincipal(portfolio.getUser());
	}

	public String getPortfolioId() {
		return portfolioId;
	}

	public Portfolio getPortfolio() {
		return new Portfolio(ownerID.getPrimaryPrincipal(), portfolioId,
				portfolioName, creationtDate);
	}

}
