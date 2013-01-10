package gabriel.yuppiewall.jpa.trade.repository;

import gabriel.yuppiewall.jpa.trade.domain.JPAPortfolio;
import gabriel.yuppiewall.jpa.trade.domain.JPATransaction;
import gabriel.yuppiewall.jpa.um.domain.JPAPrincipal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "transactionRepositorty")
public interface TransactionRepositorty extends
		JpaSpecificationExecutor<JPATransaction>,
		JpaRepository<JPATransaction, String> {

	@Query(value = "select e from JPAPortfolio e where e.portfolioName = :name and e.ownerID = :principal")
	JPAPortfolio findByPortfolioId(@Param("name") String id,
			@Param("principal") JPAPrincipal principal);

	@Query(value = "select e from JPATransaction e where e.user = :principal and e.symbol IN :symbols")
	List<JPATransaction> findTransactionDetails(
			@Param("principal") JPAPrincipal jpaPrincipal,
			@Param("symbols") List<String> symbols);

	@Query(value = "select e from JPATransaction e where e.user = :principal")
	List<JPATransaction> findTransactionDetails(
			@Param("principal") JPAPrincipal jpaPrincipal);

}
