package gabriel.yuppiewall.client;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.common.LineIterator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FilebasedEODDataReader {
	private static ExecutorService executor = Executors.newCachedThreadPool();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// will search a directory for eod data file
		File root = new File("/home/parvez/yuppiewall/data");
		System.out.println(root.isDirectory());
		File[] files = root.listFiles();

		for (File file : files) {
			List<EndOfDayData_> batch = new ArrayList<EndOfDayData_>();
			try {
				Iterator<String> itr = new LineIterator(new FileInputStream(
						file));

				while (itr.hasNext()) {
					String line = itr.next();
					EndOfDayData_ eod = ParseCSV.parse(line);// parse this line
					if (eod == null)
						continue;
					batch.add(eod);
					if (batch.size() == 100) {
						// spawn the thread which will fire the webservice call
						sendToServer(batch);
						batch = new ArrayList<EndOfDayData_>();
					}
				}
				if (batch.size() > 0) {
					// spawn the thread which will fire the webservice call
					sendToServer(batch);
				}
				batch = null;//
			} catch (FileNotFoundException ignore) {
				ignore.printStackTrace();
			}
		}

	}

	private static void sendToServer(final List<EndOfDayData_> batch) {
		/*
		 * new EndOfDayServiceRestClient().saveEOD(batch .toArray(new
		 * EndOfDayData_[0]));
		 */

		executor.execute(new Runnable() {

			public void run() {

				new EndOfDayServiceRestClient().saveEOD(batch
						.toArray(new EndOfDayData_[0]));

			}
		});

	}
}
