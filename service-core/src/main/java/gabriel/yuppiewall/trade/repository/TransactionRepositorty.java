package gabriel.yuppiewall.trade.repository;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.List;

public interface TransactionRepositorty {

	void createTransaction(Transaction transaction);

	List<Transaction> getTransactionDetails(Account account,
			List<Instrument> instruments);

	List<Transaction> getAllUserParticpatedTransaction(PrimaryPrincipal user);

}
