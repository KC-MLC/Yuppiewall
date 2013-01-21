package gabriel.yuppiewall.ws.scanner.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.service.CoreScanRunner;
import gabriel.yuppiewall.scanner.service.ScanRunner;

import java.util.Iterator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/scanrunner")
public class ScanRunnerController extends CoreScanRunner implements ScanRunner {

	@RequestMapping(value = "/scan", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Override
	public List<ScanOutput> runScan(@RequestBody List<Condition> conditions,
			@RequestBody ScanRequest scanRequest) {

		return super.runScan(conditions, scanRequest);
	}

	@Override
	protected List<EndOfDayData> getSymbolEODRecord(Instrument instrument,
			ScanRequest ignore) {
		return DataStore.get(instrument);
	}

	@Override
	protected Iterator<Instrument> getSymbols(ScanRequest ignore) {

		return DataStore.keySetIterator();
	}
}
