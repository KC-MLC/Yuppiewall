package gabriel.yuppiewall.scanner.service;

import java.security.Principal;
import java.util.concurrent.ExecutorService;

import gabriel.yuppiewall.indicator.repository.TechnicalIndicatorRepository;
import gabriel.yuppiewall.marketdata.service.Scanner;
import gabriel.yuppiewall.scanner.domain.ScanRequest;

public class ScanRunner {

	public String scan(Principal principal, final ScanRequest sr) {
		final String uid = getTechnicalIndicatorRepository().createGUID(
				principal);
		getExecutorService().submit(new Runnable() {

			@Override
			public void run() {
				/*Scanner scan = getScannerRepository().getScan(sr);
				List<Stock> list = scan.scan();
				getTechnicalIndicatorRepository().storeTechnicalRecord();*/

			}
		});
		return uid;

	}

	private ScannerRepository getScannerRepository() {
		return null;
	}

	private ExecutorService getExecutorService() {
		return null;

	}

	private TechnicalIndicatorRepository getTechnicalIndicatorRepository() {
		// TODO Auto-generated method stub
		return null;
	}

}
