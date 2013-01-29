package gabriel.yuppiewall.marketdata.repository;

import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;

import java.util.Collection;
import java.util.List;

public interface SystemDataRepository {

	List<Exchange> getExchangeByCountryCode(String value);

	Collection<Server> getServerList();

	List<Server> getExchangeServerList(Exchange exchange);

	Exchange getExchange(Instrument instrument);

}
