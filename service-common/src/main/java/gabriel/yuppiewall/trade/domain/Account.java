package gabriel.yuppiewall.trade.domain;

import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Account implements Serializable {

	private PrimaryPrincipal client;
	private String accountName;
	private String currencyCode;

	private String accountId;

	private List<Portfolio> portfolios = new ArrayList<Portfolio>();
	private Date creationtDate;

	public Account() {
	}

	public Account(String accountId) {
		this.accountId = accountId;
	}

	public Account(PrimaryPrincipal client, String accountName,
			Date creationtDate, String currencyCode) {
		this.client = client;
		this.accountName = accountName;
		this.creationtDate = creationtDate;
		this.currencyCode = currencyCode;
	}

	public Account(PrimaryPrincipal client, String accountId,
			String accountName, Date creationtDate, String currencyCode) {
		this.client = client;
		this.accountName = accountName;
		this.creationtDate = creationtDate;
		this.currencyCode = currencyCode;
		this.accountId = accountId;
	}

	public PrimaryPrincipal getClient() {
		return client;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setClient(PrimaryPrincipal client) {
		this.client = client;
	}

	public void setCreationtDate(Date date) {
		this.creationtDate = date;
	}

	public Date getCreationtDate() {
		return creationtDate;
	}

	public void setPortfolio(Portfolio portfolio) {
		portfolios.add(portfolio);
	}

	public String getAccountId() {
		return accountId;
	}

	public List<Portfolio> getPortfolios() {
		return portfolios;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [client=" + client + ", accountName=" + accountName
				+ ", currencyCode=" + currencyCode + ", accountId=" + accountId
				+ ", portfolios=" + portfolios + ", creationtDate="
				+ creationtDate + "]";
	}

}
