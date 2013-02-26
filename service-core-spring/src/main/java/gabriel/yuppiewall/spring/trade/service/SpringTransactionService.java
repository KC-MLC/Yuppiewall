package gabriel.yuppiewall.spring.trade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.trade.repository.TransactionRepositorty;
import gabriel.yuppiewall.trade.service.TransactionServiceImpl;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

@Service("transactionService")
public class SpringTransactionService extends TransactionServiceImpl {

	@Autowired
	private TransactionRepositorty transactionRepositorty;

	@Override
	protected TransactionRepositorty getTransactionRepositorty() {
		return transactionRepositorty;
	}

	

}
