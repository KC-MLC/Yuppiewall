package gabriel.yuppiewall.ws.scanner.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.market.service.MarketService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.ws.scanner.service.DataStore.STATUS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class RegionServerInitilizer {

	@Autowired
	@Qualifier("dataStore")
	private DataStore dataStore;

	@Autowired
	private MarketService marketService;

	public void init() {
		// check if need is there to initilize

		if (dataStore.getStatus() == STATUS.NOT_INITIALIZED) {
			synchronized (dataStore) {
				if (dataStore.getStatus() == STATUS.NOT_INITIALIZED) {
					dataStore.setStatus(STATUS.IN_PROGRESS);
					dataStore.clear();
					try {

						// get List of stock I am managing
						Instrument[] symbolList = getManagedInstrumentList();
						for (Instrument instrument : symbolList) {

							Exchange exchange = instrument.getExchange();
							Exchange e1 = dataStore.getExchange(exchange);
							if (e1 == null) {
								exchange = getExchange(exchange);
								dataStore.setExchange(exchange);
								instrument.setExchange(exchange);
							}
							List<EndOfDayData> eodList = marketService
									.getEndOfDayData(instrument);
							instrument.setEodList(eodList);
							dataStore.setInstrument(instrument);
						}

					} catch (Exception e) {
						dataStore.setStatus(STATUS.NOT_INITIALIZED);
					}
				}
			}
		}

	}

	private Exchange getExchange(Exchange exchange) {
		// return marketService.getExchange(exchange);
		return null;
	}

	private Instrument[] getManagedInstrumentList() {
		// find my id
		/*
		 * String requestID = dataStore.generateRequestID(); // make server call
		 * to update my id RegionServerService regionServerService;
		 * 
		 * return
		 * regionServerService.getManagedInstrument(dataStore.getStoreID());
		 */
		return null;

	}
}
