package gabriel.yuppiewall.jpa.trade.repository;

import gabriel.yuppiewall.jpa.trade.domain.JPATransaction;
import gabriel.yuppiewall.trade.domain.Transaction;

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

}
