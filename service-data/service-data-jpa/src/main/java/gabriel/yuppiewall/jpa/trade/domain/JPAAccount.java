package gabriel.yuppiewall.jpa.trade.domain;

import gabriel.yuppiewall.jpa.um.domain.JPAPrincipal;
import gabriel.yuppiewall.trade.domain.Account;

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
@Table(name = "account")
public class JPAAccount implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "account_id")
	private Long accountId;

	@OneToOne
	@JoinColumn(name = "principal_id")
	private JPAPrincipal client;
	@Column(name = "account_name", nullable = false, length = 24)
	private String accountName;
	@Column(name = "currency_code", nullable = false)
	private String currencyCode;

	@Column(name = "creation_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationtDate;

	public JPAAccount(Long accountId) {
		this.accountId = accountId;
	}

	public JPAAccount() {

	}

	public JPAAccount(Account account) {
		this.accountName = account.getAccountName();
		this.client = new JPAPrincipal(account.getClient());
		this.creationtDate = account.getCreationtDate();
		this.currencyCode = account.getCurrencyCode();
		if (account.getAccountId() != null)
			accountId = Long.parseLong(account.getAccountId());
	}

	public Account getAccount() {
		return new Account(client.getPrimaryPrincipal(), accountId.toString(),
				accountName, creationtDate, currencyCode);
	}
}
