package gabriel.yuppiewall.jpa.trade.repository;

import gabriel.yuppiewall.jpa.trade.domain.JPAPortfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository(value = "transactionRepositorty")
public interface TransactionRepositorty extends
		JpaSpecificationExecutor<JPATransaction>,
		JpaRepository<JPATransaction, String> {

}
