package gabriel.yuppiewall.spring.scanner.service;

import gabriel.yuppiewall.indicator.service.SimpleTechnicalIndicatorService;
import gabriel.yuppiewall.indicator.service.TechnicalIndicatorService;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.scanner.service.CoreScanRunner;
import gabriel.yuppiewall.scanner.service.ScanRunner;
import gabriel.yuppiewall.scanner.service.ScannerServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("scannerService")
public class SpringScannerService extends ScannerServiceImpl {

	private TechnicalIndicatorService technicalIndicatorService = new SimpleTechnicalIndicatorService();
	private ScanRunner runner = new CoreScanRunner();

	@Autowired
	@Qualifier("JDBCInMemoryMarketdata")
	private EndOfDayDataRepository endOfDayDataRepository;

	@Override
	protected TechnicalIndicatorService getTechnicalIndicatorService() {
		return technicalIndicatorService;
	}

	@Override
	protected EndOfDayDataRepository getEndOfDayDataRepository() {
		return endOfDayDataRepository;
	}

	@Override
	protected ScanRunner getScanRunner() {
		return runner;
	}

}
