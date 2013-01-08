package gabriel.yuppiewall.jpa.trade.repository;

import java.util.ArrayList;
import java.util.List;

import gabriel.yuppiewall.jpa.trade.domain.JPAPortfolio;
import gabriel.yuppiewall.jpa.um.domain.JPAPrincipal;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "JpaPortfolioRepositorty")
public class JpaPortfolioRepositorty implements
		gabriel.yuppiewall.trade.repository.PortfolioRepositorty {

	@Autowired
	private PortfolioRepositorty portfolioRepositorty;

	@Override
	public String findPortfolioId(Portfolio portfolio) {

		JPAPortfolio p = portfolioRepositorty.findByPortfolioId(
				portfolio.getPortfolioName(),
				new JPAPrincipal(portfolio.getUser()));
		return (p == null) ? null : p.getPortfolioId();
	}

	@Override
	public void createPortfolio(Portfolio portfolio) {
		JPAPortfolio jpaPortfolio = new JPAPortfolio(portfolio);
		portfolioRepositorty.save(jpaPortfolio);
	}

	@Override
	public List<Portfolio> findAllPortfolio(PrimaryPrincipal user) {
		List<JPAPortfolio> p = portfolioRepositorty
				.findAllByUser(new JPAPrincipal(user));
		List<Portfolio> retValue = new ArrayList<>();
		for (JPAPortfolio jpaPortfolio : p) {
			retValue.add(jpaPortfolio.getPortfolio());
		}
		return retValue;
	}
}
