package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.List;

public interface ScannerServive {

	List<ScanOutput> runScan(ScanParameter param, PrimaryPrincipal requester);

}
