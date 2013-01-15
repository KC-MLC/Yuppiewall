package gabriel.yuppiewall.si.scanner.service;

import java.security.Principal;
import java.util.List;

import gabriel.yuppiewall.indicator.service.SimpleTechnicalIndicatorService;
import gabriel.yuppiewall.indicator.service.TechnicalIndicatorService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.scanner.service.ScannerServiceImpl;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component(value = "scannerServiceSI")
public class ScannerService extends ScannerServiceImpl {

	private TechnicalIndicatorService technicalIndicatorService = new SimpleTechnicalIndicatorService();

	@Autowired
	@Qualifier("JPAEndOfDayDataRepository")
	private EndOfDayDataRepository endOfDayDataRepository;

	@Override
	@ServiceActivator
	public List<EndOfDayData_> runScan(ScanParameter param,
			PrimaryPrincipal requester) {
		System.out.println("In SCAN SI");
		return super.runScan(param, requester);
	}

	@Override
	protected TechnicalIndicatorService getTechnicalIndicatorService() {
		return technicalIndicatorService;
	}

	@Override
	protected EndOfDayDataRepository getEndOfDayDataRepository() {
		return endOfDayDataRepository;
	}

}
