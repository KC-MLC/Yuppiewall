package gabriel.yuppiewall.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class StockDailySummaryEmulater {

	private MessageChannel channel;

	@Value("#{stockDailySummaryChannel}")
	public void setChannel(MessageChannel channel) {
		this.channel = channel;
	}

	void sendStockDailySummary(String csvRecord) {

		channel.send(MessageBuilder.withPayload(csvRecord).build());
		System.out.println("Sent - " + csvRecord);
	}

}
