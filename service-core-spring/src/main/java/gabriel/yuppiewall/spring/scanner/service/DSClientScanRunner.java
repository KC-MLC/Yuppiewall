package gabriel.yuppiewall.spring.scanner.service;

import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.service.ScanRunner;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;

public class DSClientScanRunner implements ScanRunner {

	ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
	private URL url;
	private String id;

	public DSClientScanRunner(Server server) throws Exception {

		String strURL = server.getServerContext() + "/scanrunner/scan";
		url = new URL(strURL);
		id = server.getServerContext();
	}

	@Override
	public ScanOutput[] runScan(ScanRequest scanRequest) {
		// DefaultHttpClient client = new DefaultHttpClient();
		HttpURLConnection conn;
		try {
			String jsonValue = mapper.writeValueAsString(scanRequest);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Length",
					Integer.toString(jsonValue.length()));
			//System.out.println(jsonValue);
			conn.getOutputStream().write(jsonValue.getBytes());
			conn.getOutputStream().flush();
			conn.connect();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
				System.out.println("POST method failed: "
						+ conn.getResponseCode() + "\t"
						+ conn.getResponseMessage());

			} else {
				InputStream responseContent = (InputStream) conn.getContent();
				ScanOutput[] output = mapper.readValue(responseContent,
						ScanOutput[].class);
				return output;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return id;
	}

}
