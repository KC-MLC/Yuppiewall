package gabriel.yuppiewall.indicator;

import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SampleData {

	/**
	 * @param args
	 * @return
	 * @throws ParseException
	 */
	public static StockDailySummary_[] setup() throws ParseException {
		StockDailySummary_[] historical = new StockDailySummary_[30];

		historical[0] = set("24-Mar-10", new BigDecimal(22.27));
		historical[1] = set("25-Mar-10", new BigDecimal(22.19));
		historical[2] = set("26-Mar-10", new BigDecimal(22.08));
		historical[3] = set("29-Mar-10", new BigDecimal(22.17));
		historical[4] = set("30-Mar-10", new BigDecimal(22.18));
		historical[5] = set("31-Mar-10", new BigDecimal(22.13));
		historical[6] = set("1-Apr-10", new BigDecimal(22.23));
		historical[7] = set("5-Apr-10", new BigDecimal(22.43));
		historical[8] = set("6-Apr-10", new BigDecimal(22.24));
		historical[9] = set("7-Apr-10", new BigDecimal(22.29));
		historical[10] = set("8-Apr-10", new BigDecimal(22.15));
		historical[11] = set("9-Apr-10", new BigDecimal(22.39));
		historical[12] = set("12-Apr-10", new BigDecimal(22.38));
		historical[13] = set("13-Apr-10", new BigDecimal(22.61));
		historical[14] = set("14-Apr-10", new BigDecimal(23.36));
		historical[15] = set("15-Apr-10", new BigDecimal(24.05));
		historical[16] = set("16-Apr-10", new BigDecimal(23.75));
		historical[17] = set("19-Apr-10", new BigDecimal(23.83));
		historical[18] = set("20-Apr-10", new BigDecimal(23.95));
		historical[19] = set("21-Apr-10", new BigDecimal(23.63));
		historical[20] = set("22-Apr-10", new BigDecimal(23.82));
		historical[21] = set("23-Apr-10", new BigDecimal(23.87));
		historical[22] = set("26-Apr-10", new BigDecimal(23.65));
		historical[23] = set("27-Apr-10", new BigDecimal(23.19));
		historical[24] = set("28-Apr-10", new BigDecimal(23.10));
		historical[25] = set("29-Apr-10", new BigDecimal(23.33));
		historical[26] = set("30-Apr-10", new BigDecimal(22.68));
		historical[27] = set("3-May-10", new BigDecimal(23.10));
		historical[28] = set("4-May-10", new BigDecimal(22.40));
		historical[29] = set("5-May-10", new BigDecimal(22.17));

		return historical;
	}

	public static StockDailySummary_[] setupADL() throws ParseException {
		StockDailySummary_[] historical = new StockDailySummary_[4];
		historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		historical[1] = set("13-Dec-10", 62.05, 60.69, 60.81, 11692);
		historical[2] = set("14-Dec-10", 62.27, 60.10, 60.45, 10575);
		historical[3] = set("15-Dec-10", 60.79, 58.61, 59.18, 13059);
		/*
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 * historical[0] = set("10-Dec-10", 62.34, 61.37, 62.15, 7849);
		 */

		return historical;
	}

	private static StockDailySummary_ set(String date, double high, double low,
			double close, long volume) throws ParseException {
		return new StockDailySummary_("", "",
				new SimpleDateFormat("dd-MMM-yy").parse(date), new BigDecimal(
						0L), new BigDecimal(high), new BigDecimal(low),
				new BigDecimal(0L), BigInteger.valueOf(volume), new BigDecimal(
						close));
	}

	public static StockDailySummary_[] setupRSI() throws ParseException {
		StockDailySummary_[] historical = new StockDailySummary_[33];

		historical[0] = set("14-Dec-09", new BigDecimal(44.34));
		historical[1] = set("15-Dec-09", new BigDecimal(44.09));
		historical[2] = set("16-Dec-09", new BigDecimal(44.15));
		historical[3] = set("17-Dec-09", new BigDecimal(43.61));
		historical[4] = set("18-Dec-09", new BigDecimal(44.33));
		historical[5] = set("21-Dec-09", new BigDecimal(44.83));
		historical[6] = set("22-Dec-09", new BigDecimal(45.10));
		historical[7] = set("23-Dec-09", new BigDecimal(45.42));
		historical[8] = set("24-Dec-09", new BigDecimal(45.84));
		historical[9] = set("28-Dec-09", new BigDecimal(46.08));
		historical[10] = set("29-Dec-09", new BigDecimal(45.89));
		historical[11] = set("30-Dec-09", new BigDecimal(46.03));
		historical[12] = set("31-Dec-09", new BigDecimal(45.61));
		historical[13] = set("4-Jan-10", new BigDecimal(46.28));
		historical[14] = set("5-Jan-10", new BigDecimal(46.28));
		historical[15] = set("6-Jan-10", new BigDecimal(46.00));
		historical[16] = set("7-Jan-10", new BigDecimal(46.03));
		historical[17] = set("8-Jan-10", new BigDecimal(46.41));
		historical[18] = set("11-Jan-10", new BigDecimal(46.22));
		historical[19] = set("12-Jan-10", new BigDecimal(45.63));
		historical[20] = set("13-Jan-10", new BigDecimal(46.21));
		historical[21] = set("14-Jan-10", new BigDecimal(46.25));
		historical[22] = set("15-Jan-10", new BigDecimal(45.71));
		historical[23] = set("19-Jan-10", new BigDecimal(46.45));
		historical[24] = set("20-Jan-10", new BigDecimal(45.78));
		historical[25] = set("21-Jan-10", new BigDecimal(45.35));
		historical[26] = set("22-Jan-10", new BigDecimal(44.03));
		historical[27] = set("25-Jan-10", new BigDecimal(44.18));
		historical[28] = set("26-Jan-10", new BigDecimal(44.22));
		historical[29] = set("27-Jan-10", new BigDecimal(44.57));
		historical[30] = set("28-Jan-10", new BigDecimal(43.42));
		historical[31] = set("29-Jan-10", new BigDecimal(42.66));
		historical[32] = set("1-Feb-10", new BigDecimal(43.13));

		return historical;

	}

	static StockDailySummary_ set(String date, BigDecimal stockPriceAdjClose)
			throws ParseException {
		return new StockDailySummary_("", "",
				new SimpleDateFormat("dd-MMM-yy").parse(date), new BigDecimal(
						0L), new BigDecimal(0L), new BigDecimal(0L),
				new BigDecimal(0L), new BigInteger("0"), stockPriceAdjClose);
	}

}