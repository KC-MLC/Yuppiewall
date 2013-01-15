package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.List;

public interface ScannerServive {

	List<EndOfDayData> runScan(ScanParameter param, PrimaryPrincipal requester);

}
