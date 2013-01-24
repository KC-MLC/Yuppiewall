package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.ArrayList;
import java.util.List;

public abstract class ScannerServiceImpl implements ScannerServive {

	@Override
	public ScanOutput[] runScan(final ScanParameter param,
			final PrimaryPrincipal requester) {

		List<Condition> conditions = param.getConditions();
		if (conditions == null) {
			conditions = new ArrayList<>();
			param.setConditions(conditions);
		}
		//
		Condition avePriceConition = param.getGlobalFilter().getAvgPrice();

		if (avePriceConition != null) {
			conditions.add(avePriceConition);
		}
		Condition aveVolConition = param.getGlobalFilter().getAvgVolue();
		if (aveVolConition != null) {
			conditions.add(aveVolConition);
		}
		// get group filter
		ScanRequest scanRequest = getEndOfDayDataRepository()
				.createScanRequest(param);

		

		return getScanRunner().runScan(scanRequest);
	}

	

	

	protected abstract ScanRunner getScanRunner();

	protected abstract EndOfDayDataRepository getEndOfDayDataRepository();

}
