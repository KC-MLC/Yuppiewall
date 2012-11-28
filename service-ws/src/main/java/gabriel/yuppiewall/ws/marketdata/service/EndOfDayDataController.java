package gabriel.yuppiewall.ws.marketdata.service;

import gabriel.yuppiewall.market.service.MarketService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.service.EndOfDayServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/endofday")
public class EndOfDayDataController extends EndOfDayServiceImpl {

	@Autowired
	private EndOfDayDataRepository endOfDayDataRepository;
	@Autowired
	private MarketService marketService;

	/*
	 * @RequestMapping(value = "/accountProfile/{id}", method =
	 * RequestMethod.GET) public ResponseEntity<Accountprofile> find(
	 * 
	 * @PathVariable("id") final Integer id) {
	 * 
	 * }
	 */

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void saveEOD(@RequestBody EndOfDayData_ eod) {

		super.saveEOD(eod);
	}

	@Override
	protected EndOfDayDataRepository getEndOfDayDataRepository() {
		return endOfDayDataRepository;
	}

	@Override
	protected MarketService getMarketService() {
		return marketService;
	}

}
