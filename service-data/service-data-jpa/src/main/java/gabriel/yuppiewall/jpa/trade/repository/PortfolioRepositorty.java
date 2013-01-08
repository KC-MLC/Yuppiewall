package gabriel.yuppiewall.jpa.trade.repository;

import java.util.List;

import gabriel.yuppiewall.jpa.trade.domain.JPAPortfolio;
import gabriel.yuppiewall.jpa.um.domain.JPAPrincipal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "portfolioRepositorty")
public interface PortfolioRepositorty extends
		JpaSpecificationExecutor<JPAPortfolio>,
		JpaRepository<JPAPortfolio, String> {

	@Query(value = "select e from JPAPortfolio e where e.portfolioName = :name and e.ownerID = :principal")
	JPAPortfolio findByPortfolioId(@Param("name") String id,
			@Param("principal") JPAPrincipal principal);

	@Query(value = "select e from JPAPortfolio e where e.ownerID = :principal")
	List<JPAPortfolio> findAllByUser(
			@Param("principal") JPAPrincipal jpaPrincipal);
}
