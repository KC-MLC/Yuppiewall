package gabriel.yuppiewall.client;

import gabriel.yuppiewall.common.LineIterator;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ReadFile {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		//
		File file = new File(
				"/home/parvez/rnd/Yuppiewall/standalone-client/BSE_NSE.txt");
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"/home/parvez/rnd/Yuppiewall/standalone-client/BSE_NSE1.txt")));
		Iterator<String> itr = new LineIterator(new FileInputStream(file));
		Set<Instrument> list = new HashSet<Instrument>();

		while (itr.hasNext()) {
			String line = itr.next();

			String[] v = line.split("\t");
			if (v.length == 5) {

				if (list.add(new Instrument(v[0], new Exchange(v[4]), v[1])))
					System.out.println("(" + list.size() + ") READING-->"
							+ v[0] + "\t" + v[1] + "\t" + v[4]);
				out.println(v[0] + "\t" + v[1] + "\t" + v[4]);
			}
		}
		out.flush();
		out.close();

	}
}
