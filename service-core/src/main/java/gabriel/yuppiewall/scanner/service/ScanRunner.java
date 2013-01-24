package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.ScanOutput;

public interface ScanRunner {

	public String getId();

	public ScanOutput[] runScan(ScanRequest scanRequest);
}
