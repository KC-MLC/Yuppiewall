package gabriel.yuppiewall.trade.service;

import gabriel.yuppiewall.common.Validate;
import gabriel.yuppiewall.common.util.ValidationUtil;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Order;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.List;

public interface AccountManager {

	public static Validate<Account> validateCreateAccount = new Validate<Account>() {

		@Override
		public Account validate(Account account) {
			account = ValidationUtil.notNull(account,
					"account must not be null");
			// do validation on account having currency type and name
			account.setCurrencyCode(ValidationUtil.notNullNormalize(
					account.getCurrencyCode(), "currency code must not be null"));
			account.setAccountName(ValidationUtil.notNullNormalize(
					account.getAccountName(), "Account name must not be null"));
			return account;
		}
	};
	public static Validate<Order> validatePlaceOrder = new Validate<Order>() {

		@Override
		public Order validate(Order order) {
			order = ValidationUtil.notNull(order, "order must not be null");
			// do validation on account having mandatory value

			ValidationUtil.notNull(order.getTransactionType(),
					"TransactionType must not be null");
			ValidationUtil.notNull(order.getDate(), "Date must not be null");
			ValidationUtil.notNull(order.getInstrument(),
					"Instrument must not be null");
			ValidationUtil.notNull(order.getInstrument().getSymbol(),
					"Instrument must not be null");
			ValidationUtil.notNull(order.getPrice(), "Price must not be null");
			ValidationUtil.notNull(order.getQuantity(),
					"Quantity must not be null");
			return order;
		}
	};

	Account createAccount(Account account);

	List<Account> getAllAccountByPrincipal(PrimaryPrincipal principal);

	List<Account> getAccountPortfolioList(PrimaryPrincipal principal);

	void placeOrder(Order order, Portfolio portfolio);

	List<Transaction> getTransactions(Portfolio portfolio);

	List<Instrument> getAllInstrument(PrimaryPrincipal user);
}
