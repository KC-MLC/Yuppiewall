package gabriel.yuppiewall.trade.repository;

import java.util.List;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

public interface TransactionRepositorty {

	void createTransaction(Transaction transaction);

	List<Transaction> getTransactionDetails(PrimaryPrincipal user,
			List<Instrument> instruments);

	List<Transaction> getTransactionDetails(PrimaryPrincipal user);

}
