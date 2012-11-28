package gabriel.yuppiewall.ws.marketdata.repository;

import java.util.List;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component("siEndOfDayDataRepository")
public class SIEndOfDayDataRepository implements EndOfDayDataRepository {

	private MessageChannel channel;

	@Value("#{saveEndOfDayData}")
	public void setChannel(MessageChannel channel) {
		this.channel = channel;
	}

	@Override
	public void createEndOfDayData(EndOfDayData_ endOfDayData) {
		channel.send(MessageBuilder.withPayload(endOfDayData).build());
		System.out.println("Send Activity for save");
	}

	@Override
	public void createEndOfDayData(List<EndOfDayData_> list) {
		for (EndOfDayData_ endOfDayData : list) {
			channel.send(MessageBuilder.withPayload(endOfDayData).build());
		}

	}
}