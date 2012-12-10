package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.scanner.domain.ScanParameter;

import java.security.Principal;

public interface ScannerServive {

	String runScan(ScanParameter param, Principal requester);

}
