package gabriel.yuppiewall.spring.scanner.service;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.ScanParameter;

import java.util.List;

public class DSEndOfDayDataRepository implements EndOfDayDataRepository {

	@Override
	public void createEndOfDayData(EndOfDayData endOfDayData) {
		throw new UnsupportedOperationException(
				"not implemented>createEndOfDayData");

	}

	@Override
	public void createEndOfDayData(List<EndOfDayData> list) {
		throw new UnsupportedOperationException(
				"not implemented>createEndOfDayData");
	}

	@Override
	public ScanRequest createScanRequest(ScanParameter param) {
		//TODO access the cache and create a scan Repo
		return null;
	}
	
}
