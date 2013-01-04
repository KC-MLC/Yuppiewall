package gabriel.yuppiewall.trade.service;

import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Order;
import gabriel.yuppiewall.trade.domain.Portfolio;

public interface AccountManager {

	void placeOrder(Account account, Portfolio portfolio, Order order);
}
