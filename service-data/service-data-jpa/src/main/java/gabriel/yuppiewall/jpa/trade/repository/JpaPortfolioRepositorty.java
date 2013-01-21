package gabriel.yuppiewall.jpa.trade.repository;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.jpa.er.EntityRelation;
import gabriel.yuppiewall.jpa.er.EntityRelationRepository;
import gabriel.yuppiewall.jpa.trade.domain.JPAPortfolio;
import gabriel.yuppiewall.jpa.um.domain.JPAPrincipal;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "JpaPortfolioRepositorty")
public class JpaPortfolioRepositorty implements
		gabriel.yuppiewall.trade.repository.PortfolioRepositorty {

	@Autowired
	private PortfolioRepositorty portfolioRepositorty;
	@Autowired
	private EntityRelationRepository entityRelationRepository;

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
			retValue.add(jpaPortfolio.getPortfolio(user));
		}
		return retValue;
	}

	@Override
	public void attachIfNotpresent(Portfolio portfolio, Instrument instrument) {
		boolean stat = entityRelationRepository.doesRelationExist(
				portfolio.getPortfolioId(), instrument.getSymbol(),
				JPAPortfolio.REL_HAS_INSTRUMENT);
		if (!stat) {
			entityRelationRepository.save(new EntityRelation(portfolio
					.getPortfolioId(), instrument.getSymbol(),
					JPAPortfolio.REL_HAS_INSTRUMENT));
		}

	}

	@Override
	public List<Instrument> getPortfolioInstrument(Portfolio portfolio) {
		List<EntityRelation> instruments = entityRelationRepository
				.getEntityRHSId(portfolio.getPortfolioId(),
						JPAPortfolio.REL_HAS_INSTRUMENT);

		if (instruments.size() == 0)
			return new ArrayList<>();
		// else
		List<Instrument> retvalue = new ArrayList<>(instruments.size());
		for (EntityRelation er : instruments) {
			retvalue.add(new Instrument(er.getEntityIdRHS()));
		}
		return retvalue;

	}
}
