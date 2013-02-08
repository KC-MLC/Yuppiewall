package gabriel.yuppiewall.trade.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.List;

public interface TransactionService {

	void createTransaction(Transaction transaction);

	List<Transaction> getTransactionDetails(PrimaryPrincipal user,
			List<Instrument> instruments);

	List<Transaction> getTransactionDetails(PrimaryPrincipal user);

}