package gabriel.yuppiewall.spring.scanner.service;

import gabriel.yuppiewall.indicator.Constant;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.scanner.service.ScanRunner;
import gabriel.yuppiewall.scanner.service.ScannerServiceImpl;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("scannerService")
public class SpringScannerService extends ScannerServiceImpl {

	// private ScanRunner runner = new CoreScanRunner();
	@Autowired
	@Qualifier("distributedScanRunnerImpl")
	private ScanRunner runner;
	@Autowired
	@Qualifier("DSEndOfDayDataRepository")
	private EndOfDayDataRepository endOfDayDataRepository;

	@Override
	protected EndOfDayDataRepository getEndOfDayDataRepository() {
		return endOfDayDataRepository;
	}

	@Override
	protected ScanRunner getScanRunner() {
		return runner;
	}

	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		om.writeValueAsBytes(new Constant());
	}

}
