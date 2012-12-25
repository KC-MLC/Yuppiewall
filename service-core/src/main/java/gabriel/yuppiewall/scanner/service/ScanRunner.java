package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.scanner.domain.ScanParameter;

import java.security.Principal;
import java.util.List;

public interface ScanRunner {

	public List<EndOfDayData_> runScan(ScanParameter param, Principal requester);
}
