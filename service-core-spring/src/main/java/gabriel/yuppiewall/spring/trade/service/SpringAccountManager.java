package gabriel.yuppiewall.spring.trade.service;

import gabriel.yuppiewall.market.service.MarketService;
import gabriel.yuppiewall.market.service.MarketServiceImpl;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.trade.repository.AccountRepositorty;
import gabriel.yuppiewall.trade.repository.PortfolioRepositorty;
import gabriel.yuppiewall.trade.service.AccountManagerImpl;
import gabriel.yuppiewall.trade.service.PortfolioService;
import gabriel.yuppiewall.trade.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("accountManager")
public class SpringAccountManager extends AccountManagerImpl {

	@Autowired
	private PortfolioService portfolioService;
	@Autowired
	private PortfolioRepositorty portfolioRepositorty;

	@Autowired
	private AccountRepositorty accountRepositorty;

	@Autowired
	private TransactionService transactionService;
	// @Autowired
	private MarketService marketService = new MarketServiceImpl();
	@Autowired
	@Qualifier("SystemDataRepository")
	private SystemDataRepository systemDataRepository;

	@Override
	protected PortfolioService getPortfolioManager() {
		return portfolioService;
	}

	@Override
	protected TransactionService getTransactionService() {
		return transactionService;
	}

	@Override
	protected MarketService getMarketService() {
		return marketService;
	}

	@Override
	protected SystemDataRepository getSystemDataRepository() {
		return systemDataRepository;
	}

	@Override
	protected AccountRepositorty getAccountRepositorty() {
		return accountRepositorty;
	}

	@Override
	protected PortfolioRepositorty getPortfolioRepositorty() {
		return portfolioRepositorty;
	}

}
