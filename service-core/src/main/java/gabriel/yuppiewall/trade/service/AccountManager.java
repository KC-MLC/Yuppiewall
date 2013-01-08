package gabriel.yuppiewall.trade.service;

import java.util.List;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Order;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

public interface AccountManager {

	void placeOrder(Account account, Portfolio portfolio, Order order);
	List<Transaction> getTransactions(Portfolio portfolio);
	
	List<Instrument> getAllInstrument(PrimaryPrincipal user);
}
