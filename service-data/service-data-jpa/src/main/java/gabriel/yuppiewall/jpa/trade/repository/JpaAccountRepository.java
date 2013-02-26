package gabriel.yuppiewall.jpa.trade.repository;

import java.util.ArrayList;
import java.util.List;

import gabriel.yuppiewall.jpa.trade.domain.JPAAccount;
import gabriel.yuppiewall.jpa.um.domain.JPAPrincipal;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "JpaAccountRepository")
public class JpaAccountRepository implements
		gabriel.yuppiewall.trade.repository.AccountRepositorty {

	@Autowired
	private AccountRepositorty accountRepositorty;

	@Override
	public Account findAccountId(Account account) {

		JPAAccount p = accountRepositorty
				.findByPrincipalAccountName(account.getAccountName(),
						new JPAPrincipal(account.getClient()));
		return (p == null) ? null : p.getAccount();
	}

	@Override
	public Account createAccount(Account account) {
		return accountRepositorty.save(new JPAAccount(account)).getAccount();
	}

	@Override
	public List<Account> findAllAccount(PrimaryPrincipal principal) {
		List<JPAAccount> accountList = accountRepositorty
				.findAllAccount(new JPAPrincipal(principal));
		List<Account> retValue = new ArrayList<Account>();
		if (accountList == null)
			return null;
		for (JPAAccount account : accountList) {
			retValue.add(account.getAccount());
		}
		return retValue;
	}
}
