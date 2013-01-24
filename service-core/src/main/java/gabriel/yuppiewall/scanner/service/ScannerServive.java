package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

public interface ScannerServive {

	ScanOutput[] runScan(ScanParameter param, PrimaryPrincipal requester);

}
