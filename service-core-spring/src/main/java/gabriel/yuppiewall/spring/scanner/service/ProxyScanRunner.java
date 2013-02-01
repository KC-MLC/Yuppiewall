package gabriel.yuppiewall.spring.scanner.service;

import gabriel.util.rpc.http.HTTPProxy;
import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.service.ScanRunner;

import java.util.List;

public class ProxyScanRunner implements ScanRunner {

	private Server server;

	public ProxyScanRunner(Server server) {
		this.server = server;
	}

	@Override
	public String getId() {
		return server.getServerContext();
	}

	@Override
	public List<ScanOutput> runScan(ScanRequest scanRequest) {
		String url = server.getServerContext().replaceAll("/api", "");
		HTTPProxy proxy = new HTTPProxy(url);
		try {
			ScanRunner scanRunner = (ScanRunner) proxy
					.getService(ScanRunner.class);
			return scanRunner.runScan(scanRequest);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
