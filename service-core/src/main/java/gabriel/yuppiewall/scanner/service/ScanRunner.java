package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.ScanOutput;

import java.util.List;

public interface ScanRunner {

	public String getId();

	public List<ScanOutput> runScan(ScanRequest scanRequest);
}
