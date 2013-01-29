package gabriel.yuppiewall.spring.scanner.service;

import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.service.ScanRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("distributedScanRunnerImpl")
public class DistributedScanRunnerImpl implements ScanRunner {

	private static final String RUNNER_ID = DistributedScanRunnerImpl.class
			.getName();

	@Autowired
	private SystemDataRepository systemDataRepository;

	private Map<String, ScanRunner> wsScanRunnerList;

	private void init() {
		wsScanRunnerList = new HashMap<>();
		List<Server> serverList = systemDataRepository.getServerList();
		for (Server server : serverList) {
			try {
				wsScanRunnerList.put(server.getServerContext(),
						new DSClientScanRunner(server));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public ScanOutput[] runScan(ScanRequest scanRequest) {
		if (wsScanRunnerList == null) {
			init();
		}
		Iterator<String> itr = scanRequest.getExchanges().iterator();
		List<ScanRunner> scanRunners = new ArrayList<>();
		while (itr.hasNext()) {
			List<Server> serverList = systemDataRepository
					.getExchangeServerList(new Exchange(itr.next()));
			if (serverList == null)
				continue;
			for (Server server : serverList) {
				scanRunners
						.add(wsScanRunnerList.get(server.getServerContext()));
			}

		}

		Thread[] threads = new Thread[scanRunners.size()];
		RunScan[] runScan = new RunScan[scanRunners.size()];

		int j = 0;
		for (int i = j = 0; i < scanRunners.size(); i++, j++) {
			ScanRunner scanRunner = scanRunners.get(i);
			(threads[j] = new Thread(runScan[j] = new RunScan(scanRequest,
					scanRunner))).start();
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
			if (output == null)
				continue;
			size += output.length;
		}
		ScanOutput[] retValue = new ScanOutput[size];
		int destPos = 0;
		for (int i = 0; i < runScan.length; i++) {

			ScanOutput[] output = runScan[i].getOutput();
			if (output == null)
				continue;
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
