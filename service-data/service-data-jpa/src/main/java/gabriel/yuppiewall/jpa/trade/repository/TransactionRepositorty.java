package gabriel.yuppiewall.jpa.trade.repository;

import gabriel.yuppiewall.jpa.trade.domain.JPAAccount;
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

	@Query(value = "select e from JPATransaction e where e.account = :account and e.symbol IN :symbols")
	List<JPATransaction> findTransactionDetails(
			@Param("account") JPAAccount jpaAccount,
			@Param("symbols") List<String> symbols);

	@Query(value = "select e from JPATransaction e,JPAAccount a where e.account = a and a.client =:principal")
	List<JPATransaction> findAllUserParticpatedTransaction(
			@Param("principal") JPAPrincipal jpaPrincipal);

}
