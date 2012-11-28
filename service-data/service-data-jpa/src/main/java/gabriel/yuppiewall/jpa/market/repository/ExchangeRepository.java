package gabriel.yuppiewall.jpa.market.repository;

import gabriel.yuppiewall.jpa.market.domain.JPAExchange;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository(value = "JPAExchangeRepository")
public interface ExchangeRepository extends
		JpaSpecificationExecutor<JPAExchange>,
		JpaRepository<JPAExchange, String> {

}
