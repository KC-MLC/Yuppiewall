package gabriel.yuppiewall.jpa.market.repository;

import gabriel.yuppiewall.jpa.market.domain.JPAExchange;
import gabriel.yuppiewall.jpa.market.domain.JPAInstrument;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "JPAInstrumentRepository")
public interface InstrumentRepository extends
		JpaSpecificationExecutor<JPAInstrument>,
		JpaRepository<JPAInstrument, String> {

	@Query(value = "select e.exchange from JPAInstrument e where e =:instrument")
	JPAExchange getExchangeByInstrument(
			@Param("instrument") JPAInstrument instrument);

}
