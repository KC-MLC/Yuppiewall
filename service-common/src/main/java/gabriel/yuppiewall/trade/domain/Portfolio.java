package gabriel.yuppiewall.trade.domain;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Portfolio implements Serializable {

	private String portfolioId;
	private String portfolioName;
	private Account account;

	private Date creationtDate;

	// private List<Transaction> transactions;

	public Portfolio() {
	}

	public Portfolio(String portfolioId) {
		this.portfolioId = portfolioId;
	}

	public Portfolio(Account account, String portfolioId, String portfolioName,
			Date creationtDate) {
		this.account = account;
		this.portfolioId = portfolioId;
		this.portfolioName = portfolioName;
		this.creationtDate = creationtDate;
	}

	public Portfolio(Account account, String portfolioName) {
		this.account = account;
		this.portfolioName = portfolioName;
	}

	public Account getAccount() {
		return account;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
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
