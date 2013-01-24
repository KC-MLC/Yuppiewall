package gabriel.yuppiewall.ws.scanner.service;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/cache")
public class EndOfDayCacheController {

	@Autowired
	@Qualifier("dataStore")
	private DataStore dataStore;

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void put(@RequestBody List<EndOfDayData> eodList) {

		dataStore.addAll(eodList);

	}

	@RequestMapping(value = "/size", method = RequestMethod.GET)
	public @ResponseBody
	Integer size() {
		return dataStore.getSize();
	}

	@RequestMapping(value = "/clear", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void clear() {
		dataStore.clear();
	}
}
