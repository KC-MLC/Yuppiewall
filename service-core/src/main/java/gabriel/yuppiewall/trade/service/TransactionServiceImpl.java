package gabriel.yuppiewall.trade.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.trade.repository.TransactionRepositorty;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.List;

public abstract class TransactionServiceImpl implements TransactionService {

	@Override
	public void createTransaction(Transaction transaction) {
		getTransactionRepositorty().createTransaction(transaction);
	}

	@Override
	public List<Transaction> getTransactionDetails(Account account,
			List<Instrument> instruments) {
		return getTransactionRepositorty().getTransactionDetails(account,
				instruments);
	}

	@Override
	public List<Transaction> getAllUserParticpatedTransaction(
			PrimaryPrincipal user) {
		return getTransactionRepositorty().getAllUserParticpatedTransaction(
				user);
	}

	protected abstract TransactionRepositorty getTransactionRepositorty();

}
