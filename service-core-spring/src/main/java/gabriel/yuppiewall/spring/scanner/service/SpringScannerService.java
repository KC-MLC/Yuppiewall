package gabriel.yuppiewall.spring.scanner.service;

import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.scanner.service.ScanRunner;
import gabriel.yuppiewall.scanner.service.ScannerServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("scannerService")
public class SpringScannerService extends ScannerServiceImpl {

	@Autowired
	@Qualifier("distributedScanRunnerImpl")
	private ScanRunner runner;
	@Autowired
	@Qualifier("JDBCSystemDataRepository")
	private SystemDataRepository systemDataRepository;

	@Override
	protected ScanRunner getScanRunner() {
		return runner;
	}

	@Override
	protected SystemDataRepository getSystemDataRepository() {
		return systemDataRepository;
	}

}
