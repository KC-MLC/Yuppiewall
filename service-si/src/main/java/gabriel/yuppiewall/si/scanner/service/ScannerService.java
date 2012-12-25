package gabriel.yuppiewall.si.scanner.service;

import java.security.Principal;
import java.util.List;

import gabriel.yuppiewall.indicator.service.TechnicalIndicatorService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.scanner.service.ScannerServiceImpl;

import org.springframework.stereotype.Component;

@Component(value = "scannerServiceSI")
public class ScannerService extends ScannerServiceImpl {

	@Override
	public List<EndOfDayData_> runScan(ScanParameter param, Principal requester) {
		return super.runScan(param, requester);
	}

	@Override
	protected TechnicalIndicatorService getTechnicalIndicatorService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected EndOfDayDataRepository getEndOfDayDataRepository() {
		// TODO Auto-generated method stub
		return null;
	}

}
