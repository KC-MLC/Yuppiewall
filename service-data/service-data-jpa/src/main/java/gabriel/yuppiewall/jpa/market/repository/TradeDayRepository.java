package gabriel.yuppiewall.jpa.market.repository;

import gabriel.yuppiewall.jpa.market.domain.JPAExchange;
import gabriel.yuppiewall.jpa.market.domain.JPATradeDay;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "JPATradeDayRepository")
public interface TradeDayRepository extends
		JpaSpecificationExecutor<JPATradeDay>,
		JpaRepository<JPATradeDay, String> {

	@Query(value = "select e from JPATradeDay e where e.date =(select max(e1.date) from JPATradeDay e1 where e1.exchange=:exchange) and e.exchange = :exchange")
	JPATradeDay findLastTradeDayByExchange(
			@Param("exchange") JPAExchange exchange);

	@Query(value = "select e from JPATradeDay e where e.date =(select max(e1.date) from JPATradeDay e1 where e1.exchange=:exchange and e1.date < :date) and e.exchange = :exchange")
	JPATradeDay findLastTradeDayBeforeDate(@Param("date") Date date,
			@Param("exchange") JPAExchange exchange);

	@Modifying
	@Query(value = "UPDATE JPATradeDay td SET td.businessday = td.businessday + 1 WHERE td.date > :date AND td.exchange= :exchange)")
	void incrementBusinessdayBy1(@Param("date") Date date,
			@Param("exchange") JPAExchange exchange);
}
