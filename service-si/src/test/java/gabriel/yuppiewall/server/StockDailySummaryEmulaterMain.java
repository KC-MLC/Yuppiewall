package gabriel.yuppiewall.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StockDailySummaryEmulaterMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String contextName = "spring-integration-context.xml";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				contextName);
		applicationContext.start();
		
		
		
		

	}

}
