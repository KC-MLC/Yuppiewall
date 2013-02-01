package gabriel.yuppiewall.jpa.marketdata.repository;

import java.util.List;

import gabriel.yuppiewall.jpa.market.domain.JPAExchange;
import gabriel.yuppiewall.jpa.market.domain.JPAInstrument;
import gabriel.yuppiewall.jpa.marketdata.domain.JPAEndOfDayData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "JPAEODDataRepository")
public interface JPAEODDataRepository extends
		JpaSpecificationExecutor<JPAEndOfDayData>,
		JpaRepository<JPAEndOfDayData, String> {

	@Query(value = "SELECT e FROM JPAEndOfDayData e WHERE e.instrument.exchange=:exchange ORDER BY e.date DESC")
	List<JPAEndOfDayData> findAllByExchange(
			@Param("exchange") JPAExchange exchange);

	@Query(value = "SELECT e FROM JPAEndOfDayData e WHERE e.instrument.exchange.country=:country ORDER BY e.date DESC")
	List<JPAEndOfDayData> findAllByCountry(
			@Param("country") String country);

	@Query(value = "SELECT e FROM JPAEndOfDayData e WHERE e.instrument =:instrument ORDER BY e.date DESC")
	List<JPAEndOfDayData> findAllEndOfDayData(@Param("instrument") JPAInstrument jpaInstrument);

}
