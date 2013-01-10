package gabriel.yuppiewall.spring.trade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gabriel.yuppiewall.trade.repository.PortfolioRepositorty;
import gabriel.yuppiewall.trade.service.PortfolioServiceImpl;

@Service("portfolioService")
public class SpringPortfolioService extends PortfolioServiceImpl {

	@Autowired
	private PortfolioRepositorty portfolioRepositorty;

	@Override
	protected PortfolioRepositorty getPortfolioRepositorty() {
		return portfolioRepositorty;
	}

}
