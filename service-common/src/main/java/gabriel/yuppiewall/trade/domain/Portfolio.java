package gabriel.yuppiewall.trade.domain;

import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Portfolio implements Serializable {

	private PrimaryPrincipal user;
	private String portfolioId;
	private String portfolioName;

	private Date creationtDate;

	//private List<Transaction> transactions;

	public Portfolio() {
	}

	public Portfolio(PrimaryPrincipal user, String portfolioId,
			String portfolioName, Date creationtDate) {
		this.user = user;
		this.portfolioId = portfolioId;
		this.portfolioName = portfolioName;
		this.creationtDate = creationtDate;
	}

	public Portfolio(PrimaryPrincipal user, String portfolioName) {
		this.user = user;
		this.portfolioName = portfolioName;
	}

	public PrimaryPrincipal getUser() {
		return user;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	public void setPortfolioId(String portfolioId) {
		this.portfolioId = portfolioId;
	}

	public Date getCreationtDate() {
		return creationtDate;
	}

	public String getPortfolioId() {
		return portfolioId;
	}

	public void setCreationtDate(Date creationtDate) {
		this.creationtDate = creationtDate;
	}

	@Override
	public String toString() {
		return getPortfolioName();
	}

}
