package gabriel.yuppiewall.jpa.trade.repository;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.jpa.trade.domain.JPAAccount;
import gabriel.yuppiewall.jpa.trade.domain.JPATransaction;
import gabriel.yuppiewall.jpa.um.domain.JPAPrincipal;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "JpaTransactionRepositorty")
public class JpaTransactionRepositorty implements
		gabriel.yuppiewall.trade.repository.TransactionRepositorty {
	@Autowired
	private TransactionRepositorty transactionRepositorty;

	@Override
	public void createTransaction(Transaction transaction) {
		transactionRepositorty.save(new JPATransaction(transaction));
	}

	@Override
	public List<Transaction> getTransactionDetails(Account account,
			List<Instrument> instruments) {
		List<String> symbols = new ArrayList<>(instruments.size());
		for (Instrument instrument : instruments) {
			symbols.add(instrument.getSymbol());
		}
		List<JPATransaction> list = transactionRepositorty
				.findTransactionDetails(new JPAAccount(account), symbols);
		return makeTransactionList(list, account);

	}

	private List<Transaction> makeTransactionList(List<JPATransaction> list,
			Account account) {
		List<Transaction> retvalue = new ArrayList<>(list.size());
		for (JPATransaction jpaTransaction : list) {
			retvalue.add(jpaTransaction.getTransaction(account));
		}
		return retvalue;
	}

	private List<Transaction> makeTransactionList(List<JPATransaction> list) {
		List<Transaction> retvalue = new ArrayList<>(list.size());
		for (JPATransaction jpaTransaction : list) {
			retvalue.add(jpaTransaction.getTransaction());
		}
		return retvalue;
	}

	@Override
	public List<Transaction> getAllUserParticpatedTransaction(
			PrimaryPrincipal user) {

		List<JPATransaction> list = transactionRepositorty
				.findAllUserParticpatedTransaction(new JPAPrincipal(user));
		return makeTransactionList(list);
	}
}
