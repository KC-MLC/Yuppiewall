package gabriel.yuppiewall.ws.scanner.service;

import gabriel.yuppiewall.common.util.Filter;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

@Service
public class EodScheduler {

	@Autowired
	private TaskScheduler scheduler;
	@Autowired
	private DataStore dataStore;
	@Autowired
	private EndOfDayDataRepository eodRepository;

	void createScheduler(Exchange exchange) {
		String tz = exchange.getTimeZone();
		// "0 30 16 * * MON-FRI"
		scheduler.schedule(new EodDataScheduler(exchange), new CronTrigger(
				exchange.getMarketCloseSchedule(), TimeZone.getTimeZone(tz)));

	}

	class EodDataScheduler implements Runnable {
		private Exchange exchange;

		EodDataScheduler(Exchange exchange) {
			this.exchange = exchange;
		}

		@Override
		public void run() {
			Iterator<Instrument> list = dataStore
					.getInstrumentFromExchange(exchange);
			if (list != null) {
				while (list.hasNext()) {
					Instrument instrument = list.next();
					final Date lastUpdate = instrument.getEodList().get(0)
							.getDate();
					List<EndOfDayData> eodList = eodRepository
							.findAllEndOfDayData(instrument,
									new Filter<EndOfDayData>() {

										@Override
										public boolean filter(EndOfDayData t) {
											return t.getDate()
													.after(lastUpdate);
										}
									});
					instrument.setEodList(eodList);
					dataStore.setInstrument(instrument);
				}
			}

		}
	};

}
