package gabriel.yuppiewall.trade.repository;

import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.List;

public interface AccountRepositorty {

	Account findAccountId(Account account);

	List<Account> findAllAccount(PrimaryPrincipal principal);

	Account createAccount(Account account);

}
