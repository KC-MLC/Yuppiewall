package gabriel.yuppiewall.ws.scanner.service;

import java.util.List;

import gabriel.util.rpc.http.RPCServlet;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.service.ScanRunner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings("serial")
@WebServlet("/ScanRunner")
public class ScanRunnerServlet extends RPCServlet implements ScanRunner {

	private ScanRunner scanRunner;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		WebApplicationContext appContext = WebApplicationContextUtils
				.getWebApplicationContext(servletConfig.getServletContext());
		scanRunner = (ScanRunnerController) appContext
				.getBean("ScanRunnerController");
	}

	@Override
	public String getId() {
		return scanRunner.getId();
	}

	@Override
	public List<ScanOutput> runScan(ScanRequest scanRequest) {
		return scanRunner.runScan(scanRequest);
	}

}
