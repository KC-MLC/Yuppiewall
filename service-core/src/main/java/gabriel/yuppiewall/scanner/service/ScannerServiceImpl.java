package gabriel.yuppiewall.scanner.service;

import gabriel.yuppiewall.indicator.repository.TechnicalIndicatorRepository;
import gabriel.yuppiewall.scanner.domain.ScanParameter;

import java.security.Principal;
import java.util.concurrent.ExecutorService;

public abstract class ScannerServiceImpl implements ScannerServive {

	@Override
	public String runScan(final ScanParameter param, final Principal requester) {

		// get a new request ID
		final String uid = getTechnicalIndicatorRepository().createGUID(
				requester);
		param.setScanId(uid);
		getExecutorService().submit(new Runnable() {

			@Override
			public void run() {
				getScanRunner().runScan(param, requester);
			}
		});
		return uid;
	}

	protected abstract ScanRunner getScanRunner();

	protected abstract ExecutorService getExecutorService();

	protected abstract TechnicalIndicatorRepository getTechnicalIndicatorRepository();

}
