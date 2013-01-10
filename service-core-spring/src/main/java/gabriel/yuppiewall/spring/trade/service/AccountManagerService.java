package gabriel.yuppiewall.spring.trade.service;

import gabriel.yuppiewall.market.service.MarketService;
import gabriel.yuppiewall.trade.service.AccountManagerImpl;
import gabriel.yuppiewall.trade.service.PortfolioService;
import gabriel.yuppiewall.trade.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountManager")
public class AccountManagerService extends AccountManagerImpl {

	@Autowired
	private PortfolioService portfolioService;

	@Override
	protected PortfolioService getPortfolioManager() {
		return portfolioService;
	}

	@Override
	protected TransactionService getTransactionService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MarketService getMarketService() {
		// TODO Auto-generated method stub
		return null;
	}

}
