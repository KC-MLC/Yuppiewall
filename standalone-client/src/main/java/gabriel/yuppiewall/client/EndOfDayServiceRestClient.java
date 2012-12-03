package gabriel.yuppiewall.client;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.marketdata.service.EndOfDayService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;

public class EndOfDayServiceRestClient implements EndOfDayService {

	// @Override
	public void saveEOD(EndOfDayData_[] data) {
		try {

			URL url = new URL(
					"http://localhost:8080/service-ws/api/endofday/bulk");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			ObjectMapper mapper = new ObjectMapper();

			byte[] postdata = mapper.writeValueAsBytes(data);

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
