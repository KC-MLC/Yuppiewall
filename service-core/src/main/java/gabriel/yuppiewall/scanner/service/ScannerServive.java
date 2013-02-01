package gabriel.yuppiewall.scanner.service;

import java.util.List;

import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

public interface ScannerServive {

	List<ScanOutput> runScan(ScanParameter param, PrimaryPrincipal requester);

}
