package gabriel.yuppiewall.jpa.market.repository;

import gabriel.yuppiewall.jpa.market.domain.JPAExchange;
import gabriel.yuppiewall.jpa.market.domain.JPATradeDay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TradeDayRepository extends
		JpaSpecificationExecutor<JPATradeDay>,
		JpaRepository<JPATradeDay, String> {

	JPATradeDay findLastTradeDayByExchange(JPAExchange exchange);

}
