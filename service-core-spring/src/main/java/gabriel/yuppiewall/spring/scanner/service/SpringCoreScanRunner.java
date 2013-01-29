package gabriel.yuppiewall.spring.scanner.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import gabriel.yuppiewall.indicator.service.TechnicalIndicatorService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.service.CoreScanRunner;

public class SpringCoreScanRunner extends CoreScanRunner {

	@Autowired
	private 
	@Override
	protected List<EndOfDayData> getSymbolEODRecord(String instrument) {
		
		return null;
	}

	@Override
	protected Collection<String> getSymbols(ScanRequest scanRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TechnicalIndicatorService getTechnicalIndicatorService() {
		// TODO Auto-generated method stub
		return null;
	}

}
