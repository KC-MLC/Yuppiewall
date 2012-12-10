package gabriel.yuppiewall.jpa.marketdata.repository;

import java.util.List;

import gabriel.yuppiewall.jpa.market.domain.JPAExchange;
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

	@Query(value = "select e from JPAEndOfDayData e where e.exchange=:exchange ORDER BY e.date")
	List<JPAEndOfDayData> findAll(@Param("exchange") JPAExchange exchange);

}
