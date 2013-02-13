package gabriel.yuppiewall.client;

import gabriel.yuppiewall.common.LineIterator;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FilebasedEODDataReaderV4 {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// will search a directory for eod data file
		File root = new File("/home/parvez/yuppiewall/data");
		File output = new File("/home/parvez/yuppiewall/schemaZX.txt");
		System.out.println(root.isDirectory());
		Map<String, List<EndOfDayData>> dataList = new HashMap<String, List<EndOfDayData>>();

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

					EndOfDayData eod = ParseCSV.parse(name[0] + "," + name[1]
							+ "," + line);// parse this line
					if (eod == null)
						continue;
					Calendar cal = Calendar.getInstance();
					cal.setTime(eod.getDate());
					if (cal.get(Calendar.YEAR) < 2011)
						continue;

					List<EndOfDayData> list = dataList.get(eod.getInstrument()
							.getExchange().getSymbol()
							+ eod.getDate().getTime());
					if (list == null) {
						list = new ArrayList<EndOfDayData>();
						dataList.put(eod.getInstrument().getExchange()
								.getSymbol()
								+ eod.getDate().getTime(), list);
					}
					list.add(eod);
				}

				System.out.println("OVER = "
						+ (System.currentTimeMillis() - start));

			} catch (FileNotFoundException ignore) {
				ignore.printStackTrace();
			}
		}
		if (!output.exists()) {
			output.createNewFile();
		}
		FileWriter fw = new FileWriter(output.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		/*
		 * String v =
		 * "INSERT INTO end_of_day_data (identifier, trade_date, stock_price_adj_close, stock_price_close, stock_price_high,"
		 * +
		 * " stock_price_low, stock_price_open, stock_volume, symbol, exchange)"
		 * + " VALUES "; bw.write(v);
		 */
		int[] i = new int[1];
		for (Iterator<String> iterator = dataList.keySet().iterator(); iterator
				.hasNext();) {
			String key = iterator.next();
			List<EndOfDayData> list = dataList.get(key);
			System.out.println(key);
			sendToServer(list, bw, i);
			list.clear();

		}
		// bw.write(";");
		bw.flush();
		bw.close();
	}

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private static void sendToServer(List<EndOfDayData> list,
			BufferedWriter bw, int[] i) throws IOException {
		/*
		 * new EndOfDayServiceRestClient().saveEOD(batch .toArray(new
		 * EndOfDayData_[0]));
		 */
		System.out.println("sending>>" + list.size());

		for (EndOfDayData eod : list) {
			String identifier = eod.getInstrument().getExchange().getSymbol()
					+ eod.getInstrument().getSymbol() + eod.getDate().getTime();

			String v = identifier + "," + sdf.format(eod.getDate()) + ","
					+ eod.getStockPriceAdjClose() + ","
					+ eod.getStockPriceClose() + "," + eod.getStockPriceHigh()
					+ "," + eod.getStockPriceLow() + ","
					+ eod.getStockPriceOpen() + "," + eod.getStockVolume()
					+ "," + eod.getInstrument().getSymbol() + ","
					+ eod.getInstrument().getExchange().getSymbol();
			System.out.println(i[0]++);
			bw.write(v);
			bw.newLine();

		}

		// new EndOfDayServiceRestClient().saveEOD(data);
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
