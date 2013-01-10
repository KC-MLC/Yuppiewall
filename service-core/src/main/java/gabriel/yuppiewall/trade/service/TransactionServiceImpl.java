package gabriel.yuppiewall.trade.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.trade.repository.TransactionRepositorty;

import java.util.List;

public abstract class TransactionServiceImpl implements TransactionService {

	@Override
	public void createTransaction(Transaction transaction) {
		getTransactionRepositorty().createTransaction(transaction);
	}

	@Override
	public List<Transaction> getTransactionDetails(List<Instrument> instruments) {
		// TODO Auto-generated method stub
		return null;
	}

	protected abstract TransactionRepositorty getTransactionRepositorty();

}
