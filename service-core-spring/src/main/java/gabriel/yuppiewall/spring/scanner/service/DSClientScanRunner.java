package gabriel.yuppiewall.spring.scanner.service;

import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.ScanOutput;
import gabriel.yuppiewall.scanner.service.ScanRunner;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

public class DSClientScanRunner implements ScanRunner {

	ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
	private URL url;

	public void init(Map<String, String> property) throws Exception {
		String strURL = property.get("serverAddress");
		url = new URL(strURL);
	}

	@Override
	public List<ScanOutput> runScan(List<Condition> conditionList,
			ScanRequest scanRequest) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			byte[] conditionListByte = mapper.writeValueAsBytes(conditionList);
			byte[] conditionScanRequest = mapper.writeValueAsBytes(scanRequest);
			OutputStream os = conn.getOutputStream();
			os.write(conditionListByte);
			os.write(conditionScanRequest);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getId() {
		return url.toString();
	}

	@Override
	public String toString() {
		return "" + url;
	}

}
