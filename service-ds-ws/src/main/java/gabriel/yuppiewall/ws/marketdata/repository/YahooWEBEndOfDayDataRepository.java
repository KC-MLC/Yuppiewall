package gabriel.yuppiewall.ws.marketdata.repository;

import gabriel.yuppiewall.common.LineIterator;
import gabriel.yuppiewall.common.util.Filter;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class YahooWEBEndOfDayDataRepository implements EndOfDayDataRepository {
	private static Proxy SYSTEM_PROXY;

	static {
		// System.setProperty("java.net.useSystemProxies", "true");
		SYSTEM_PROXY = Proxy.NO_PROXY;

		/*SYSTEM_PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				"43.80.41.41", 8080));*/

	}

	@Override
	public void createEndOfDayData(EndOfDayData endOfDayData) {
		throw new UnsupportedOperationException("method not implemented");

	}

	@Override
	public void createEndOfDayData(List<EndOfDayData> list) {
		throw new UnsupportedOperationException("method not implemented");

	}

	@Override
	public List<EndOfDayData> findAllEndOfDayData(Instrument instrument,
			Filter<EndOfDayData> filter) {
		String symbol = URLEncoder.encode(instrument.getSymbol());
		try {

			URL url = new URL(
					"http://ichart.finance.yahoo.com/table.csv?d=12&e=18&f=2000&g=d&a=12&b=18&c=2013&ignore=.csv&s="
							+ symbol);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection(SYSTEM_PROXY);
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			InputStream is = urlConnection.getInputStream();
			List<EndOfDayData> list = new ArrayList<>();
			System.out
					.println("http://ichart.finance.yahoo.com/table.csv?d=12&e=18&f=2000&g=d&a=12&b=18&c=2013&ignore=.csv&s="
							+ symbol);
			LineIterator lineItr = new LineIterator(is);
			// skip to second line
			if (lineItr.hasNext())
				lineItr.next();
			while (lineItr.hasNext()) {
				String line = lineItr.next();
				// System.out.println("LINE_->" + line);
				EndOfDayData eod = parse(line, instrument);
				if (!filter.filter(eod))
					break;

				list.add(eod);

			}
			lineItr.close();
			return list;
		} catch (Exception e) {
			System.out.println(", " + symbol);
			// e.printStackTrace();

		}
		return null;
	}

	public static void main(String[] args) {
		new YahooWEBEndOfDayDataRepository().findAllEndOfDayData(
				new Instrument("IBM", new Exchange("nyse")),
				new Filter<EndOfDayData>() {

					@Override
					public boolean filter(EndOfDayData t) {
						return true;
					}
				});
	}

	private static final int DATE = 0;
	private static final int STOCKPRICEOPEN = 1;
	private static final int STOCKPRICEHIGH = 2;
	private static final int STOCKPRICELOW = 3;
	private static final int STOCKPRICECLOSE = 4;
	private static final int STOCKVOLUME = 5;
	private static final int STOCKPRICEADJCLOSE = 6;

	public static EndOfDayData parse(String csv, Instrument instrument) {
		String values[] = csv.split(",");

		try {

			return new EndOfDayData(instrument, new SimpleDateFormat(
					"yyyy-MM-dd").parse(values[DATE]), new BigDecimal(
					values[STOCKPRICEOPEN]), new BigDecimal(
					values[STOCKPRICEHIGH]), new BigDecimal(
					values[STOCKPRICELOW]), new BigDecimal(
					values[STOCKPRICECLOSE]), new BigDecimal(
					values[STOCKVOLUME]), new BigDecimal(
					values[STOCKPRICEADJCLOSE]));
		} catch (Exception ignore) {
			ignore.printStackTrace();
			System.out.println("FAILED TO PARSE:" + csv);
			return null;
		}

	}

}
