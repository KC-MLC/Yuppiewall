package gabriel.yuppiewall.ws.scanner.service;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/cache")
public class EndOfDayCacheController {

	@RequestMapping(value = "/put", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	// @Consumes("application/json")
	public void put(@RequestBody List<EndOfDayData> eodList) {

		DataStore.addAll(eodList);
	
	}

	@RequestMapping(value = "/size", method = RequestMethod.GET)
	// @Consumes("application/json")
	public Integer size() {
		return DataStore.getSize();
	}

	@RequestMapping(value = "/clear", method = RequestMethod.DELETE)
	// @Consumes("application/json")
	public void clear() {
		DataStore.clear();
	}
}
