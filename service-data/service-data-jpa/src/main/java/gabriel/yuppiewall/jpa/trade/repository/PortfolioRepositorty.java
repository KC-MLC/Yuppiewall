package gabriel.yuppiewall.jpa.trade.repository;

import gabriel.yuppiewall.jpa.trade.domain.JPAAccount;
import gabriel.yuppiewall.jpa.trade.domain.JPAPortfolio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "portfolioRepositorty")
public interface PortfolioRepositorty extends
		JpaSpecificationExecutor<JPAPortfolio>,
		JpaRepository<JPAPortfolio, Long> {

	@Query(value = "select e from JPAPortfolio e where e.portfolioName = :name and e.account = :account")
	JPAPortfolio findPortfolioByName(@Param("account") JPAAccount jpaAccount,
			@Param("name") String portfolioName);

	@Query(value = "select e from JPAPortfolio e where e.account IN :accountList")
	List<JPAPortfolio> findAllAccountPortfolio(
			@Param("accountList") List<JPAAccount> jpaAccountList);

}
