package gabriel.yuppiewall.jpa.marketdata.repository;

import gabriel.yuppiewall.jpa.marketdata.domain.JPAEndOfDayData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository(value = "JPAEODDataRepository")
public interface JPAEODDataRepository extends
		JpaSpecificationExecutor<JPAEndOfDayData>,
		JpaRepository<JPAEndOfDayData, String> {

}
