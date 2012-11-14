package gabriel.yuppiewall.server;

import gabriel.yuppiewall.marketdata.repository.StockDailySummaryRepository;
import gabriel.yuppiewall.server.util.LineIterator;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StockDailySummaryEmulaterMain {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		String contextName[] = new String[] {
				"/META-INF/spring/integration/spring-integration-context.xml",
				"/META-INF/spring/spring-context.xml" };
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				contextName);
		applicationContext.start();

		InputStream testDataStream = StockDailySummaryEmulaterMain.class
				.getResourceAsStream("AMEX_daily_prices_A.csv");

		StockDailySummaryRepository stockDailySummaryRepository = applicationContext
				.getBean("stockDailySummaryRepositoryMC",
						StockDailySummaryRepository.class);

		LineIterator feed = new LineIterator(testDataStream);
		while (feed.hasNext()) {
			stockDailySummaryRepository.saveStockDailySummary(feed.next());
		}
	}
}
