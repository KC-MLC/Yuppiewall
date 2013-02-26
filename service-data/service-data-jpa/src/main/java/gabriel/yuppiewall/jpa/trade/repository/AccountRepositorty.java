package gabriel.yuppiewall.jpa.trade.repository;

import java.util.List;

import gabriel.yuppiewall.jpa.trade.domain.JPAAccount;
import gabriel.yuppiewall.jpa.um.domain.JPAPrincipal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "accountRepositorty")
public interface AccountRepositorty extends
		JpaSpecificationExecutor<JPAAccount>, JpaRepository<JPAAccount, Long> {

	@Query(value = "select e from JPAAccount e where e.accountName = :accountName and e.client = :client")
	JPAAccount findByPrincipalAccountName(
			@Param("accountName") String accountName,
			@Param("client") JPAPrincipal client);

	@Query(value = "select e from JPAAccount e where e.client = :client")
	List<JPAAccount> findAllAccount(@Param("client") JPAPrincipal jpaPrincipal);

}