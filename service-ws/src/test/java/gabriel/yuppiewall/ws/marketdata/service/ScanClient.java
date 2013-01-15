package gabriel.yuppiewall.ws.marketdata.service;

import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.scanner.domain.Expression;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.scanner.domain.ScanParameter.OPERAND;
import gabriel.yuppiewall.scanner.domain.ScanParameter.PERIOD;
import gabriel.yuppiewall.scanner.domain.ScanParameter.SCAN_ON;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;

public class ScanClient {

	public static void main(String[] args) {

		try {

			URL url = new URL(
					"http://localhost:8082/service-ws/api/scan/process");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			ObjectMapper mapper = new ObjectMapper();
			ScanParameter sp = new ScanParameter();

			byte[] postdata = mapper.writeValueAsBytes(sp);
			System.out.println(new String(postdata));

			OutputStream os = conn.getOutputStream();
			os.write(postdata);
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
			throw new RuntimeException();

		}

	}
}
