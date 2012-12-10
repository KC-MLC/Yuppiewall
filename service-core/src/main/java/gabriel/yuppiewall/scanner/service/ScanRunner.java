package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.scanner.domain.ScanParameter;

import java.security.Principal;

public interface ScanRunner {

	public void runScan(ScanParameter param, Principal requester);
}
