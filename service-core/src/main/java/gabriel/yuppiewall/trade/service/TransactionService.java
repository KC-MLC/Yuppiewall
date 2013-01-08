package gabriel.yuppiewall.trade.service;

import java.util.List;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Transaction;

public interface TransactionService {

	void createTransaction(Transaction transaction);

	List<Transaction> getTransactionDetails(List<Instrument> instruments);

}
