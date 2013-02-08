package gabriel.yuppiewall.trade.domain;

import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.io.Serializable;
import java.util.List;

import javax.security.auth.Subject;

public class Account implements Serializable {

	private PrimaryPrincipal client;
	private Account accountId;
	private List<Portfolio> portfolios;

	public PrimaryPrincipal getClient() {
		return client;
	}

}
