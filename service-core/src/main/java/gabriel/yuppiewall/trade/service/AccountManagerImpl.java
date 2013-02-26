package gabriel.yuppiewall.trade.service;

import gabriel.yuppiewall.common.exception.EntityAlreadyExistsException;
import gabriel.yuppiewall.common.exception.InvalidParameterValueException;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.market.service.MarketService;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.trade.domain.Account;
import gabriel.yuppiewall.trade.domain.Order;
import gabriel.yuppiewall.trade.domain.Order.TransactionType;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.trade.repository.AccountRepositorty;
import gabriel.yuppiewall.trade.repository.PortfolioRepositorty;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class AccountManagerImpl implements AccountManager {

	@Override
	public Account createAccount(Account account) {

		account = validateCreateAccount.validate(account);
		// check if name is unique
		Account temp = getAccountRepositorty().findAccountId(account);
		if (temp != null)
			throw new EntityAlreadyExistsException(
					"Account name already in use");

		account.setCreationtDate(new Date());
		account = getAccountRepositorty().createAccount(account);
		return account;
	}

	@Override
	public List<Account> getAllAccountByPrincipal(PrimaryPrincipal principal) {
		return getAccountRepositorty().findAllAccount(principal);
	}

	@Override
	public List<Account> getAccountPortfolioList(PrimaryPrincipal principal) {
		List<Account> accountList = getAllAccountByPrincipal(principal);
		if (accountList.isEmpty())
			return new ArrayList<Account>();

		List<Portfolio> portfolioList = getPortfolioRepositorty()
				.getAllAccountPortfolio(accountList);
		for (Portfolio portfolio : portfolioList) {
			int index = accountList.indexOf(portfolio.getAccount());
			Account account = accountList.get(index);
			account.setPortfolio(portfolio);
		}

		return accountList;

	}

	@Override
	public void placeOrder(Account account1, Portfolio portfolio, Order order) {

		Exchange exchange = getSystemDataRepository().getExchange(
				order.getInstrument());
		if (exchange == null)
			throw new InvalidParameterValueException("Instrument not supported");
		Date exNow = getMarketService().getExchangeCurrentTime(exchange);
		if (order.getDate().before(exNow)) {
			// this is of type just store and forget
			// create transaction
			TransactionType type = order.getTransactionType();
			if (type == TransactionType.BUY) {
				String txID = UUID.randomUUID().toString();

				addTransaction(new Transaction(txID, account1, type,
						order.getInstrument(), order.getDate(),
						order.getPrice(), order.getQuantity()));
				if (portfolio.getPortfolioId() != null)
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

	@Override
	public List<Instrument> getAllInstrument(PrimaryPrincipal user) {
		List<Instrument> retValue = new ArrayList<>();

		List<Transaction> txList = getTransactionService()
				.getAllUserParticpatedTransaction(user);
		Set<Instrument> dupList = new HashSet<>();
		for (Transaction transaction : txList) {
			if (dupList.add(transaction.getInstrument()))
				retValue.add(transaction.getInstrument());
		}
		return retValue;
	}

	@Override
	public List<Transaction> getTransactions(Portfolio portfolio) {

		List<Instrument> instruments = getPortfolioManager()
				.getPortfolioInstrument(portfolio);
		if (instruments.size() == 0)
			return new ArrayList<>();
		return getTransactionService().getTransactionDetails(
				portfolio.getAccount(), instruments);
	}

	protected abstract PortfolioService getPortfolioManager();

	private void addTransaction(Transaction transaction) {
		getTransactionService().createTransaction(transaction);

	}

	protected abstract TransactionService getTransactionService();

	protected abstract SystemDataRepository getSystemDataRepository();

	protected abstract MarketService getMarketService();

	protected abstract AccountRepositorty getAccountRepositorty();

	protected abstract PortfolioRepositorty getPortfolioRepositorty();

}
