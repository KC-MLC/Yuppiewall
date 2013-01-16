package gabriel.yuppiewall.client;

import gabriel.yuppiewall.common.LineIterator;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FilebasedEODDataReader {
	private static ExecutorService executor = Executors.newFixedThreadPool(5);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// will search a directory for eod data file
		File root = new File("/home/parvez/yuppiewall/data");
		System.out.println(root.isDirectory());
		File[] files = root.listFiles();

		for (File file : files) {
			EndOfDayData[] data = new EndOfDayData[100];
			int counter = 0;
			try {
				Iterator<String> itr = new LineIterator(new FileInputStream(
						file));
				String[] name = file.getName().split("_");
				System.out.println(file.getName());

				long start = System.currentTimeMillis();
				while (itr.hasNext()) {
					String line = itr.next();

					EndOfDayData eod = ParseCSV.parse(name[0] + "," + name[1]
							+ "," + line);// parse this line
					if (eod == null)
						continue;
					Calendar cal = Calendar.getInstance();
					cal.setTime(eod.getDate());
					if (cal.get(Calendar.YEAR) < 2000)
						continue;

					data[counter++] = eod;
					if (counter == 100) {
						// spawn the thread which will fire the webservice call
						sendToServer(data);
						data = new EndOfDayData[100];
						counter = 0;
					}
				}
				if (counter > 0) {
					// spawn the thread which will fire the webservice call
					EndOfDayData[] temp = new EndOfDayData[counter];

					System.arraycopy(data, 0, temp, 0, counter);
					sendToServer(temp);
				}
				System.out.println("OVER = "
						+ (System.currentTimeMillis() - start));
				data = null;//
			} catch (FileNotFoundException ignore) {
				ignore.printStackTrace();
			}
		}

	}

	private static void sendToServer(final EndOfDayData[] data) {
		/*
		 * new EndOfDayServiceRestClient().saveEOD(batch .toArray(new
		 * EndOfDayData_[0]));
		 */

		executor.execute(new Runnable() {

			public void run() {

				new EndOfDayServiceRestClient().saveEOD(data);

			}
		});

	}
}
