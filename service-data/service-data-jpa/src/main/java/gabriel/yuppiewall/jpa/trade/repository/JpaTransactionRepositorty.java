package gabriel.yuppiewall.jpa.trade.repository;

import java.util.ArrayList;
import java.util.List;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.jpa.trade.domain.JPATransaction;
import gabriel.yuppiewall.jpa.um.domain.JPAPrincipal;
import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

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
	public List<Transaction> getTransactionDetails(PrimaryPrincipal user,
			List<Instrument> instruments) {
		List<String> symbols = new ArrayList<>(instruments.size());
		for (Instrument instrument : instruments) {
			symbols.add(instrument.getSymbol());
		}
		List<JPATransaction> list = transactionRepositorty
				.findTransactionDetails(new JPAPrincipal(user), symbols);
		return makeTransactionList(list, user);

	}

	private List<Transaction> makeTransactionList(List<JPATransaction> list,
			PrimaryPrincipal user) {
		List<Transaction> retvalue = new ArrayList<>(list.size());
		for (JPATransaction jpaTransaction : list) {
			retvalue.add(jpaTransaction.getTransaction(user));
		}
		return retvalue;
	}

	@Override
	public List<Transaction> getTransactionDetails(PrimaryPrincipal user) {

		List<JPATransaction> list = transactionRepositorty
				.findTransactionDetails(new JPAPrincipal(user));
		return makeTransactionList(list, user);
	}
}
