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

		historical[0] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("24-Mar-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.27), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.27));
		historical[1] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("25-Mar-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.19), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.19));
		historical[2] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("26-Mar-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.08), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.08));
		historical[3] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("29-Mar-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.17), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.17));
		historical[4] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("30-Mar-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.18), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.18));
		historical[5] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("31-Mar-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.13), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.13));
		historical[6] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("1-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.23), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.23));
		historical[7] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("5-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.43), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.43));
		historical[8] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("6-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.24), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.24));
		historical[9] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("7-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.29), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.29));
		historical[10] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("8-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.15), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.15));
		historical[11] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("9-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.39), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.39));
		historical[12] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("12-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.38), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.38));
		historical[13] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("13-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.61), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.61));
		historical[14] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("14-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(23.36), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(23.36));
		historical[15] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("15-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(24.05), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(24.05));
		historical[16] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("16-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(23.75), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(23.75));
		historical[17] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("19-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(23.83), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(23.83));
		historical[18] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("20-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(23.95), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(23.95));
		historical[19] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("21-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(23.63), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(23.63));
		historical[20] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("22-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(23.82), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(23.82));
		historical[21] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("23-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(23.87), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(23.87));
		historical[22] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("26-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(23.65), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(23.65));
		historical[23] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("27-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(23.19), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(23.19));
		historical[24] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("28-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(23.10), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(23.10));
		historical[25] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("29-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(23.33), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(23.33));
		historical[26] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("30-Apr-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.68), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.68));
		historical[27] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("3-May-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(23.10), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(23.10));
		historical[28] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("4-May-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.40), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.40));
		historical[29] = new StockDailySummary_("", "", new SimpleDateFormat(
				"dd-MMM-yy").parse("5-May-10"), new BigDecimal(0L),
				new BigDecimal(0L), new BigDecimal(22.17), new BigDecimal(0L),
				new BigInteger("0"), new BigDecimal(22.17));

		return historical;

	}

}
