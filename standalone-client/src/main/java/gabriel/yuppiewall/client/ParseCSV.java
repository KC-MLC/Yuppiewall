package gabriel.yuppiewall.client;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ParseCSV {
	private static final int EXCHANGE = 0;
	private static final int STOCKSYMBOL = 1;
	private static final int DATE = 2;
	private static final int STOCKPRICEOPEN = 3;
	private static final int STOCKPRICEHIGH = 4;
	private static final int STOCKPRICELOW = 5;
	private static final int STOCKPRICECLOSE = 6;
	private static final int STOCKVOLUME = 7;
	private static final int STOCKPRICEADJCLOSE = 8;

	public static EndOfDayData parse(String csv) {
		String values[] = csv.split(",");

		try {

			return new EndOfDayData(new Instrument(values[STOCKSYMBOL],
					new Exchange(values[EXCHANGE])), new SimpleDateFormat(
					"yyyy-MM-dd").parse(values[DATE]), new BigDecimal(
					values[STOCKPRICEOPEN]), new BigDecimal(
					values[STOCKPRICEHIGH]), new BigDecimal(
					values[STOCKPRICELOW]), new BigDecimal(
					values[STOCKPRICECLOSE]), new BigDecimal(
					values[STOCKVOLUME]), new BigDecimal(
					values[STOCKPRICEADJCLOSE]));
		} catch (ParseException ignore) {
			ignore.printStackTrace();
			System.out.println("FAILED TO PARSE:" + csv);
			return null;
		}

	}
}
