package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.ScanOutput;

import java.util.List;

public interface ScanRunner {

	public List<ScanOutput> runScan(List<Condition> conditions,
			ScanRequest eodData);
}
