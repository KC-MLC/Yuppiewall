package gabriel.yuppiewall.ws.marketdata.service;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.service.EndOfDayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/endofday")
public class EndOfDayDataController implements EndOfDayService {

	/*
	 * private MessageChannel endOfDayChannel;
	 * 
	 * @Value("#{yw_eod_channel}") public void setEndOfDayChannel(MessageChannel
	 * endOfDayChannel) { this.endOfDayChannel = endOfDayChannel; }
	 */

	@Autowired
	@Qualifier("endOfDayService")
	private EndOfDayService endOfDayService;

	@RequestMapping(value = "/bulk", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.ACCEPTED)
	// @Consumes("application/json")
	public void saveEOD(@RequestBody EndOfDayData[] eod) {

		// endOfDayChannel.send(MessageBuilder.withPayload(eod).build());
		System.out.println("eod Send - ");
		endOfDayService.saveEOD(eod);
	}

}
