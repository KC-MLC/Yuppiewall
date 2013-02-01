package gabriel.yuppiewall.spring.scanner.service;

import gabriel.yuppiewall.common.util.Filter;
import gabriel.yuppiewall.indicator.service.SimpleTechnicalIndicatorService;
import gabriel.yuppiewall.indicator.service.TechnicalIndicatorService;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.scanner.service.CoreScanRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("scanRunner")
public class SpringCoreScanRunner extends CoreScanRunner {

	@Autowired
	private EndOfDayDataRepository eodRepository;
	@Autowired
	@Qualifier("JDBCSystemDataRepository")
	private SystemDataRepository systemDataRepository;

	private TechnicalIndicatorService technicalIndicatorService = new SimpleTechnicalIndicatorService();

	@Override
	protected List<EndOfDayData> getSymbolEODRecord(Instrument instrument) {

		return eodRepository.findAllEndOfDayData(instrument,
				new Filter<EndOfDayData>() {

					@Override
					public boolean filter(EndOfDayData t) {
						return true;
					}
				});
	}

	@Override
	protected LinkedList<Instrument> getInstrumentFromExchange(
			Set<String /* symbol */> exchanges) {
		return systemDataRepository.getInstrumentFromExchange(exchanges);

	}

	@Override
	protected TechnicalIndicatorService getTechnicalIndicatorService() {
		return technicalIndicatorService;
	}

}
