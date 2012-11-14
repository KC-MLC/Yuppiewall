package gabriel.yuppiewall.server.service;

import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;
import gabriel.yuppiewall.marketdata.repository.StockDailySummaryRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component("stockDailySummaryRepositoryMC")
public class StockDailySummaryRepositoryImpl implements
		StockDailySummaryRepository {

	private MessageChannel channel;

	@Value("#{stockDailySummaryChannel}")
	public void setChannel(MessageChannel channel) {
		this.channel = channel;
	}

	/*
	 * private MessagingTemplate messagingTemplate;
	 * 
	 * @Autowired public void setMessagingTemplate(MessagingTemplate
	 * messagingTemplate) { this.messagingTemplate = messagingTemplate; }
	 */

	@Override
	public void saveStockDailySummary(StockDailySummary_ stockDailySummary) {
		channel.send(MessageBuilder.withPayload(stockDailySummary).build());

		// messagingTemplate.convertAndSend(activity);
		System.out.println("Send Activity for save");
	}

	@Override
	public void saveStockDailySummary(String csvStockDailySummary) {
		channel.send(MessageBuilder.withPayload(csvStockDailySummary).build());

		// messagingTemplate.convertAndSend(activity);
	//	System.out.println("Send for save" + csvStockDailySummary);
	}

	@Override
	public void saveStockDailySummary(StockDailySummary_[] stockDailySummary) {
		throw new UnsupportedOperationException("saveStockDailySummary[]");
	}

}