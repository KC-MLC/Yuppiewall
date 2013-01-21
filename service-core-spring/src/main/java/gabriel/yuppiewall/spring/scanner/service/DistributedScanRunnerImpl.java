package gabriel.yuppiewall.spring.scanner.service;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.service.ScanRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DistributedScanRunnerImpl implements ScanRunner {

	private static final String RUNNER_ID = DistributedScanRunnerImpl.class
			.getName();
	private List<ScanRunner> wsScanRunnerList;

	private ScanRunner getScanRunner(String id) {
		for (ScanRunner scanRunner : wsScanRunnerList) {

			if (scanRunner.getId().equals(id)) {
				return scanRunner;
			}
		}
		throw new UnsupportedOperationException("SERVER ID IS NOT REGISTERED");
	}

	@Override
	public List<ScanOutput> runScan(List<Condition> conditions,
			ScanRequest scanRequest) {
		Thread[] threads = new Thread[wsScanRunnerList.size()];
		RunScan[] runScan = new RunScan[wsScanRunnerList.size()];
		System.out.println("STARTED GROUPING");
		Map<String /* server context */, List<Instrument>> dsList = new HashMap<>();
		Iterator<Instrument> itrInstrument = scanRequest.getFilteredResult()
				.iterator();
		while (itrInstrument.hasNext()) {
			Instrument instrument = (Instrument) itrInstrument.next();
			List<Instrument> instruments = dsList.get(instrument.getServer());
			if (instruments == null) {
				instruments = new ArrayList<>();
				dsList.put(instrument.getServer(), instruments);
			}
			instruments.add(instrument);
		}

		List<ScanOutput> retValue = new ArrayList<ScanOutput>();
		Iterator<String> itrServerContext = dsList.keySet().iterator();

		int j = 0;
		while (itrServerContext.hasNext()) {
			String serverContext = itrServerContext.next();

			ScanRunner scanRunner = getScanRunner(serverContext);

			(threads[j] = new Thread(runScan[j] = new RunScan(conditions,
					scanRequest, scanRunner))).start();
			j++;
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
			}
		}
		for (int i = 0; i < runScan.length; i++) {

			List<ScanOutput> output = runScan[i].getOutput();
			if (output == null) {
				System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRR aa");
				continue;
			}
			retValue.addAll(output);
		}
		return retValue;
	}

	class RunScan implements Runnable {
		private List<Condition> conditions;
		private ScanRequest scanRequest;
		private ScanRunner scanner;
		private List<ScanOutput> output;

		public RunScan(List<Condition> conditions, ScanRequest scanRequest,
				ScanRunner scanner) {
			this.conditions = conditions;
			this.scanRequest = scanRequest;
		}

		public List<ScanOutput> getOutput() {
			return output;
		}

		@Override
		public void run() {
			output = scanner.runScan(conditions, scanRequest);
		}
	}

	@Override
	public String getId() {
		return RUNNER_ID;
	}
}
