package gabriel.yuppiewall.client;

import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ParseCSV {
	private static final int EXCHANGE = 0;
	private static final int STOCKSYMBOL = 0;
	private static final int DATE = 1;
	private static final int STOCKPRICEOPEN = 2;
	private static final int STOCKPRICEHIGH = 3;
	private static final int STOCKPRICELOW = 4;
	private static final int STOCKPRICECLOSE = 5;
	private static final int STOCKVOLUME = 6;
	private static final int STOCKPRICEADJCLOSE = 5;

	public static EndOfDayData_ parse(String csv) {
		String values[] = csv.split(",");

		try {
			return new EndOfDayData_(new Exchange_(
					/* values[EXCHANGE] */"NYSE"), values[STOCKSYMBOL],
					new SimpleDateFormat("yyyyMMdd").parse(values[DATE]),
					new BigDecimal(values[STOCKPRICEOPEN]), new BigDecimal(
							values[STOCKPRICEHIGH]), new BigDecimal(
							values[STOCKPRICELOW]), new BigDecimal(/*
																	 * values[
																	 * STOCKPRICECLOSE
																	 * ]
																	 */0),
					new BigInteger(values[STOCKVOLUME]), new BigDecimal(
							values[STOCKPRICEADJCLOSE]));
		} catch (ParseException ignore) {
			ignore.printStackTrace();
			System.out.println("FAILED TO PARSE:" + csv);
			return null;
		}

	}
}
