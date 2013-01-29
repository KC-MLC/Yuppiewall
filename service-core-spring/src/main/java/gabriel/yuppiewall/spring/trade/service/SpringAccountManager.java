package gabriel.yuppiewall.spring.trade.service;

import gabriel.yuppiewall.market.service.MarketService;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.trade.service.AccountManagerImpl;
import gabriel.yuppiewall.trade.service.PortfolioService;
import gabriel.yuppiewall.trade.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountManager")
public class SpringAccountManager extends AccountManagerImpl {

	@Autowired
	private PortfolioService portfolioService;

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private MarketService marketService;
	@Autowired
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

}
