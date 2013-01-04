package gabriel.yuppiewall.ws.scanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.Gateway;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.scanner.service.ScannerServive;

@Controller
@RequestMapping(value = "/scan")
public class ScannerController implements ScannerServive {

	@Autowired
	@Qualifier("scanGateway")
	private ScannerServive scannerGateway;

	@RequestMapping(value = "/process", method = RequestMethod.POST)
	@Override
	public List<EndOfDayData_> runScan(@RequestBody ScanParameter param) {
		System.out.println("GOT THE REQUESST");
		return scannerGateway.runScan(param);
	}

}
