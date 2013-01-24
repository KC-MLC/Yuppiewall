package gabriel.yuppiewall.ws.scanner.service;

import gabriel.yuppiewall.indicator.service.SimpleTechnicalIndicatorService;
import gabriel.yuppiewall.indicator.service.TechnicalIndicatorService;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.service.CoreScanRunner;
import gabriel.yuppiewall.scanner.service.ScanRunner;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/scanrunner")
public class ScanRunnerController extends CoreScanRunner implements ScanRunner {

	private TechnicalIndicatorService technicalIndicatorService = new SimpleTechnicalIndicatorService();

	@Autowired
	@Qualifier("dataStore")
	private DataStore dataStore;

	@RequestMapping(value = "/scan", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ResponseBody
	@Override
	public ScanOutput[] runScan(@RequestBody ScanRequest scanRequest) {
		System.out.println("Pocessing equest " + scanRequest);
		ScanOutput[] retva = super.runScan(scanRequest);
		System.out.println("GOT THE VALUE " + retva.length);
		return retva;
	}

	@Override
	protected List<EndOfDayData> getSymbolEODRecord(Instrument instrument,
			ScanRequest ignore) {
		return dataStore.get(instrument);
	}

	@Override
	protected Collection<Instrument> getSymbols(ScanRequest ignore) {

		return dataStore.keySetIterator();
	}

	@Override
	protected TechnicalIndicatorService getTechnicalIndicatorService() {
		return technicalIndicatorService;
	}
}
