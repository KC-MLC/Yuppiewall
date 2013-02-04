package gabriel.yuppiewall.spring.scanner.service;

import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.service.ScanRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("distributedScanRunnerImpl")
public class DistributedScanRunnerImpl implements ScanRunner {

	private static final String RUNNER_ID = DistributedScanRunnerImpl.class
			.getName();

	@Autowired
	@Qualifier("SystemDataRepository")
	private SystemDataRepository systemDataRepository;

	private Map<String, ScanRunner> wsScanRunnerList;

	private void init() {
		wsScanRunnerList = new HashMap<>();
		Collection<Server> serverList = systemDataRepository.getServerList();
		for (Server server : serverList) {
			try {
				wsScanRunnerList.put(server.getServerContext(),
						new ProxyScanRunner(server));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public List<ScanOutput> runScan(ScanRequest scanRequest) {
		if (wsScanRunnerList == null) {
			init();
		}
		Iterator<String> itr = scanRequest.getExchanges().iterator();
		Set<ScanRunner> scanRunners = new HashSet<>();
		while (itr.hasNext()) {
			Set<Server> serverList = systemDataRepository
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

		for (ScanRunner scanRunner : scanRunners) {

			(threads[j] = new Thread(runScan[j] = new RunScan(scanRequest,
					scanRunner))).start();
			j += 1;
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
			}
		}

		List<ScanOutput> retValue = new ArrayList<>();
		for (int i = 0; i < runScan.length; i++) {

			List<ScanOutput> output = runScan[i].getOutput();
			if (output == null)
				continue;
			retValue.addAll(output);

		}
		return retValue;
	}

	class RunScan implements Runnable {
		private ScanRequest scanRequest;
		private ScanRunner scanner;
		private List<ScanOutput> output;

		public RunScan(ScanRequest scanRequest, ScanRunner scanner) {
			this.scanRequest = scanRequest;
			this.scanner = scanner;
		}

		public List<ScanOutput> getOutput() {
			return output;
		}

		@Override
		public void run() {
			try {
				output = scanner.runScan(scanRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getId() {
		return RUNNER_ID;
	}
}
