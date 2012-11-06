package gabriel.yuppiewall.server.service;

import gabriel.yuppiewall.domain.marketdata.StockDailySummary_;
import gabriel.yuppiewall.service.marketdata.StockDailySummaryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;

@Component
public class StockDailySummaryMessageHandler implements Runnable {

	final static int RECEIVE_TIMEOUT = 1000;
	private QueueChannel channel;

	@Autowired
	@Qualifier("stockDailySummaryRepositoryJpa")
	private StockDailySummaryRepository stockDailySummaryRepository;

	@Value("#{stockDailySummaryChannel}")
	public void setChannel(QueueChannel channel) {
		System.out.println("REceiver is waiting");
		this.channel = channel;
	}

	@PostConstruct
	public void startTheListner() {
		System.out.println("Receiver started");
		new Thread(this).start();
	}

	private ExecutorService executor = Executors.newFixedThreadPool(1);

	private class BatchCreater implements Callable<Boolean> {

		private final List<StockDailySummary_> batch;
		private int counter;

		public BatchCreater(List<StockDailySummary_> batch) {

			this.batch = batch;
		}

		@Override
		public Boolean call() throws Exception {
			synchronized (batch) {
				System.out.println("IN CALLLLL");
				Message<?> activity;

				counter = 0;

				while (!Thread.currentThread().isInterrupted()) {
					try {
						activity = channel.receive(RECEIVE_TIMEOUT);
						if (activity != null) {
							System.out.println("got an activity");
							batch.add((StockDailySummary_) activity
									.getPayload());
							counter++;
						}
						if (counter == 11) {
							return true;
						}

					} catch (Exception ie) {
						System.out.println("LOG THIS CASE");
						break;
					}
				}
				System.out.println("returning from thread");
				return (counter > 0);
			}
		}

		public int getCounter() {
			return counter;
		}

	};

	public void handleStockDailySummaryMessage() {
		while (true) {
			ArrayList<StockDailySummary_> activityList = new ArrayList<>(20);
			System.out.println("Spawning a hread");
			Future<Boolean> result = executor.submit(new BatchCreater(
					activityList));

			try {

				result.get(RECEIVE_TIMEOUT, TimeUnit.MILLISECONDS);
			} catch (InterruptedException | ExecutionException
					| TimeoutException e) {
				result.cancel(true);
			}

			synchronized (activityList) {
				if (activityList.size() > 0) {
					handleTicket(activityList);
				}

			}

		}
	}

	// @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor =
	// Exception.class)
	private void handleTicket(ArrayList<StockDailySummary_> activityList) {

		getStockDailySummaryRepository().saveStockDailySummary(
				activityList.toArray(new StockDailySummary_[0]));

		// dispatch the message to event server for post create activity
		System.out.println("Received Activity ");
	}

	protected StockDailySummaryRepository getStockDailySummaryRepository() {
		return stockDailySummaryRepository;
	}

	@Override
	public void run() {
		handleStockDailySummaryMessage();
	}
}
