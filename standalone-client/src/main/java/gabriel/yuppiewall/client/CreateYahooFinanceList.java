package gabriel.yuppiewall.client;

import gabriel.yuppiewall.common.LineIterator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateYahooFinanceList {
	
	private static Proxy SYSTEM_PROXY;

	static {
		// System.setProperty("java.net.useSystemProxies", "true");
		SYSTEM_PROXY = Proxy.NO_PROXY;
/*
		SYSTEM_PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				"43.80.41.41", 8080));*/

	}


	static ExecutorService executo = Executors.newFixedThreadPool(10);

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File(
				"/home/parvez/Downloads/AMEX.txt");
		Iterator<String> itr = new LineIterator(new FileInputStream(file));
		long start = System.currentTimeMillis();
		while (itr.hasNext()) {

			String line = (String) itr.next();
			String[] splits = line.split(",");
			if (splits != null && splits.length > 0) {
				String symbol = splits[1].trim();
				String market = "amex";
				// System.out.println(market + ", " + symbol);
				executo.submit(new CreateYahooFinanceList().new Run(market,
						symbol));

			}

		}

		executo.shutdown();
		while (executo.isTerminated())
			System.out.println("over=" + (System.currentTimeMillis() - start));

	}

	class Run implements Runnable {

		private String market;
		private String symbol;

		public Run(String market, String symbol) {
			this.market = market;
			this.symbol = symbol;
		}

		public void run() {

			/*
			 * http://ichart.finance.yahoo.com/table.csv?d=12&e=18&f=2012&g=d&a=12
			 * &b=18&c=2012 &ignore=.csv&s=GOOG
			 */
			try {
				
				int BUFFER_SIZE = 4096;
				URL url = new URL(
						"http://ichart.finance.yahoo.com/table.csv?d=12&e=18&f=2012&g=d&a=12&b=18&c=2013&ignore=.csv&s="
								+ symbol);
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection(SYSTEM_PROXY);
				urlConnection.setRequestMethod("GET");
				urlConnection.setDoOutput(true);
				InputStream is = urlConnection.getInputStream();
				symbol = URLEncoder.encode(symbol);

				BufferedOutputStream fos = new BufferedOutputStream(
						new FileOutputStream(new File(
								"/home/parvez/yuppiewall/data", market + "_"
										+ symbol)), BUFFER_SIZE);
				BufferedInputStream bis = new BufferedInputStream(is,
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while ((bytesRead = bis.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesRead);

				}
				fos.flush();
				fos.close();
				bis.close();
			} catch (Exception e) {
				System.out.println(market + ", " + symbol);
				// e.printStackTrace();

			}
		}
	}
}
