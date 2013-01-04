package gabriel.yuppiewall.trade.service;

import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.market.service.MarketService;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Order;
import gabriel.yuppiewall.trade.domain.Order.TransactionType;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.Date;

public abstract class AccountManagerImpl implements AccountManager {

	@Override
	public void placeOrder(Account account, Portfolio portfolio, Order order) {

		MarketService ms = getMarketService();
		Exchange_ exchange = ms.getExchange(order.getInstrument());
		if (exchange == null)
			throw new IllegalArgumentException("Not a valid instrument");
		Date exNow = ms.getExchangeCurrentTime(exchange);
		if (order.getDate().before(exNow)) {
			// this is of type just store and forget
			// create transaction
			TransactionType type = order.getTransactionType();
			if (type == TransactionType.BUY) {
				PrimaryPrincipal user = account.getClient();
				addTransaction(new Transaction(user, type,
						order.getInstrument(), order.getDate(),
						order.getPrice(), order.getQuantity()));

				getPortfolioManager().attachIfNotpresent(portfolio,
						order.getInstrument());
			} else { // TODO not supported
				throw new IllegalArgumentException("Not implented");
			}
		} else {
			// TODO not supported
			throw new IllegalArgumentException("Not implented");
		}

	}

	protected abstract PortfolioService getPortfolioManager();

	private void addTransaction(Transaction transaction) {
		getTransactionService().createTransaction(transaction);

	}

	protected abstract TransactionService getTransactionService();

	protected abstract MarketService getMarketService();

}
