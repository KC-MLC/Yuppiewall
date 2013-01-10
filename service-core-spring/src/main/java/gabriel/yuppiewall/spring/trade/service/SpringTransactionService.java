package gabriel.yuppiewall.spring.trade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gabriel.yuppiewall.trade.repository.TransactionRepositorty;
import gabriel.yuppiewall.trade.service.TransactionServiceImpl;

@Service("transactionService")
public class SpringTransactionService extends TransactionServiceImpl {

	@Autowired
	private TransactionRepositorty transactionRepositorty;

	@Override
	protected TransactionRepositorty getTransactionRepositorty() {
		return transactionRepositorty;
	}

}
