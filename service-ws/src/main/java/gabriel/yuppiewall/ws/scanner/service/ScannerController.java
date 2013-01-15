package gabriel.yuppiewall.ws.scanner.service;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.scanner.service.ScannerServive;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/scan")
public class ScannerController implements ScannerServive {

	@Autowired
	@Qualifier("scanGateway")
	private ScannerServive scannerGateway;

	@RequestMapping(value = "/process", method = RequestMethod.POST)
	@Override
	public List<EndOfDayData> runScan(@RequestBody ScanParameter param,
			@RequestBody PrimaryPrincipal requester) {
		System.out.println("GOT THE REQUESST");
		return scannerGateway.runScan(param, requester);
	}

}
