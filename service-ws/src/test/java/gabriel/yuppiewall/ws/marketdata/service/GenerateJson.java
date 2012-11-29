package gabriel.yuppiewall.ws.marketdata.service;

import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class GenerateJson {

	/**
	 * @param args
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		// exchange,stock_symbol,date,stock_price_open,stock_price_high,stock_price_low,stock_price_close,stock_volume,stock_price_adj_close
		// NYSE,AEA,2010-01-27,4.82,5.16,4.79,5.09,243500,5.09
		EndOfDayData_ data = new EndOfDayData_(new Exchange_("NYSE"), "AEA",
				new SimpleDateFormat("yyyy-mm-dd").parse("2010-01-27"),
				new BigDecimal("4.82"), new BigDecimal("5.16"), new BigDecimal(
						"4.79"), new BigDecimal("5.09"), new BigInteger(
						"243500"), new BigDecimal("5.09"));
		System.out.println(mapper.writeValueAsString(data));
		mapper.writeValue(new File("data1.json"), data);

	}
}
