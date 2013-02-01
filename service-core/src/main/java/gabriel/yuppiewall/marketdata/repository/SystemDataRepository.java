package gabriel.yuppiewall.marketdata.repository;

import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public interface SystemDataRepository {

	List<Exchange> getExchangeByCountryCode(String value);

	Collection<Server> getServerList();

	Set<Server> getExchangeServerList(Exchange exchange);

	List<Instrument> getManagedInstrument(Server server);

	Exchange getExchange(Instrument instrument);

	Exchange getExchange(Exchange exchange);

	LinkedList<Instrument> getInstrumentFromExchange(Set<String> exchanges);

	void setRegionIdentiy(String regionID);

	Instrument getInstrument(Instrument inst);

}
