package gabriel.yuppiewall.spring.scanner.service;

import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.jdbc.ds.marketdata.repository.JDBCSymbolStore;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.service.ScanRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("distributedScanRunnerImpl")
public class DistributedScanRunnerImpl implements ScanRunner {

	private static final String RUNNER_ID = DistributedScanRunnerImpl.class
			.getName();
	private List<ScanRunner> wsScanRunnerList;

	@Autowired
	@Qualifier("JDBCSymbolStore")
	private JDBCSymbolStore symbolStore;

	private void init() {
		wsScanRunnerList = new ArrayList<>();
		List<Server> serverList = symbolStore.getServerList();
		for (Server server : serverList) {
			try {
				wsScanRunnerList.add(new DSClientScanRunner(server));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private ScanRunner getScanRunner(String id) {
		for (ScanRunner scanRunner : wsScanRunnerList) {

			if (scanRunner.getId().equals(id)) {
				return scanRunner;
			}
		}
		throw new UnsupportedOperationException("SERVER ID IS NOT REGISTERED");
	}

	@Override
	public ScanOutput[] runScan(ScanRequest scanRequest) {
		if (wsScanRunnerList == null) {
			init();
		}
		Thread[] threads = new Thread[wsScanRunnerList.size()];
		RunScan[] runScan = new RunScan[wsScanRunnerList.size()];
		System.out.println("STARTED GROUPING");
		Map<String /* server context */, List<Instrument>> dsList = new HashMap<>();
		Iterator<Instrument> itrInstrument = scanRequest.getFilteredResult()
				.iterator();
		while (itrInstrument.hasNext()) {
			Instrument instrument = (Instrument) itrInstrument.next();
			if (instrument.getServer() == null) {
				System.out.println("CHECK THIS");
			}
			List<Instrument> instruments = dsList.get(instrument.getServer());
			if (instruments == null) {
				instruments = new ArrayList<>();
				dsList.put(instrument.getServer(), instruments);
			}
			instruments.add(instrument);
		}

		Iterator<String> itrServerContext = dsList.keySet().iterator();

		int j = 0;
		while (itrServerContext.hasNext()) {
			String serverContext = itrServerContext.next();

			ScanRunner scanRunner = getScanRunner(serverContext);

			(threads[j] = new Thread(runScan[j] = new RunScan(scanRequest,
					scanRunner))).start();
			j++;
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
			}
		}
		// calculate max size
		int size = 0;
		for (int i = 0; i < runScan.length; i++) {
			ScanOutput[] output = runScan[i].getOutput();
			size += output.length;
		}
		ScanOutput[] retValue = new ScanOutput[size];
		int destPos = 0;
		for (int i = 0; i < runScan.length; i++) {

			ScanOutput[] output = runScan[i].getOutput();
			System.arraycopy(output, 0, retValue, destPos, output.length);
			destPos += output.length;

		}
		return retValue;
	}

	class RunScan implements Runnable {
		private ScanRequest scanRequest;
		private ScanRunner scanner;
		private ScanOutput[] output;

		public RunScan(ScanRequest scanRequest, ScanRunner scanner) {
			this.scanRequest = scanRequest;
			this.scanner = scanner;
		}

		public ScanOutput[] getOutput() {
			return output;
		}

		@Override
		public void run() {
			output = scanner.runScan(scanRequest);
		}
	}

	@Override
	public String getId() {
		return RUNNER_ID;
	}
}
