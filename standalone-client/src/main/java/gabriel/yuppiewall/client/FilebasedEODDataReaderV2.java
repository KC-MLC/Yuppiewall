package gabriel.yuppiewall.client;

import gabriel.yuppiewall.common.LineIterator;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FilebasedEODDataReaderV2 {
	private static ExecutorService executor = Executors.newFixedThreadPool(5);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// will search a directory for eod data file
		File root = new File("/home/parvez/yuppiewall/data");
		System.out.println(root.isDirectory());
		Map<String, List<EndOfDayData_>> dataList = new HashMap<String, List<EndOfDayData_>>();

		File[] files = root.listFiles();

		for (File file : files) {
			try {
				Iterator<String> itr = new LineIterator(new FileInputStream(
						file));
				String[] name = file.getName().split("_");
				System.out.println(file.getName());

				long start = System.currentTimeMillis();
				while (itr.hasNext()) {
					String line = itr.next();

					EndOfDayData_ eod = ParseCSV.parse(name[0] + "," + name[1]
							+ "," + line);// parse this line
					if (eod == null)
						continue;
					Calendar cal = Calendar.getInstance();
					cal.setTime(eod.getDate());
					if (cal.get(Calendar.YEAR) < 2012)
						continue;

					List<EndOfDayData_> list = dataList.get(eod.getExchange()
							.getName() + eod.getDate().getTime());
					if (list == null) {
						list = new ArrayList<EndOfDayData_>();
						dataList.put(eod.getExchange().getName()
								+ eod.getDate().getTime(), list);
					}
					list.add(eod);

					if (list.size() == 20) {
						// spawn the thread which will fire the webservice call
						System.out.println(eod.getExchange().getName()
								+ eod.getDate().getTime());
						sendToServer(list.toArray(new EndOfDayData_[0]));
						list.clear();
					}
				}

				System.out.println("OVER = "
						+ (System.currentTimeMillis() - start));

			} catch (FileNotFoundException ignore) {
				ignore.printStackTrace();
			}
		}
		for (Iterator<String> iterator = dataList.keySet().iterator(); iterator
				.hasNext();) {
			String key = iterator.next();
			List<EndOfDayData_> list = dataList.get(key);
			System.out.println(key);
			sendToServer(list.toArray(new EndOfDayData_[0]));
			list.clear();
		}

	}

	private static void sendToServer(final EndOfDayData_[] data) {
		/*
		 * new EndOfDayServiceRestClient().saveEOD(batch .toArray(new
		 * EndOfDayData_[0]));
		 */
		System.out.println("sending>>" + data.length);
		new EndOfDayServiceRestClient().saveEOD(data);
		/*
		 * try { Thread.sleep(5000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		/*
		 * executor.execute(new Runnable() { public void run() { // new
		 * EndOfDayServiceRestClient().saveEOD(data); } });
		 */

	}
}
