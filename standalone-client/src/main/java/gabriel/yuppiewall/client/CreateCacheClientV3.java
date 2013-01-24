package gabriel.yuppiewall.client;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class CreateCacheClientV3 {

	private static Proxy SYSTEM_PROXY;
	private ObjectMapper mapper = new ObjectMapper();
	private List<EndOfDayData> sendToServer = null;
	private String server_addr;
	private int count;

	public CreateCacheClientV3(String server_addr) {
		this.server_addr = server_addr;
	}

	protected void start() {
		try {
			String sql = "SELECT eod.identifier, eod.trade_date, eod.stock_price_adj_close, eod.stock_price_close,"
					+ " eod.stock_price_high, eod.stock_price_low, eod.stock_price_open, eod.stock_volume,"
					+ " eod.instrument"
					+ " FROM end_of_day_data eod, instrument i"
					+ " where i.symbol_code = eod.instrument and i.server_id = ? ORDER BY eod.instrument, eod.trade_date DESC  ";
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql,
					java.sql.ResultSet.TYPE_FORWARD_ONLY,
					java.sql.ResultSet.CONCUR_READ_ONLY);
			pstmt.setFetchSize(1000);
			pstmt.setString(1, server_addr);

			ResultSet rs = pstmt.executeQuery();
			Instrument current = null;
			while (rs.next()) {
				EndOfDayData eod = new EndOfDayData(new Instrument(
						rs.getString(9)), new Date(rs.getDate(2).getTime()),
						rs.getBigDecimal(7), rs.getBigDecimal(5),
						rs.getBigDecimal(6), rs.getBigDecimal(4),
						rs.getBigDecimal(8), rs.getBigDecimal(3));
				if (current == null) {
					current = eod.getInstrument();
					sendToServer = new ArrayList<EndOfDayData>();
				}
				if (current.equals(eod.getInstrument())) {
					sendToServer.add(eod);
				} else {

					sendToServer(sendToServer);
					sendToServer = null;
					current = null;
				}

			}

			rs.close();
			pstmt.close();
			con.close();
			sendToServer(sendToServer);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	static {
		// System.setProperty("java.net.useSystemProxies", "true");
		SYSTEM_PROXY = Proxy.NO_PROXY;
/*
		SYSTEM_PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				"43.80.41.41", 8080));*/

	}

	private void sendToServer(List<EndOfDayData> sendToServer2) {
		if (sendToServer2 == null || sendToServer2.size() == 0)
			return;
		/*
		 * executo.submit(new Runnable() {
		 * 
		 * public void run() {
		 */

		try {
			URL url = new URL(server_addr + "/cache/");
			HttpURLConnection conn = (HttpURLConnection) url
					.openConnection(SYSTEM_PROXY);
			conn.setRequestMethod("PUT");
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			String jsonValue = mapper.writeValueAsString(sendToServer2);
			conn.setRequestProperty("Content-Length",
					Integer.toString(jsonValue.length()));
			long start = System.currentTimeMillis();
			conn.getOutputStream().write(jsonValue.getBytes());
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			conn.connect();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
				System.out.println("PUT method failed: "
						+ conn.getResponseCode() + "\t"
						+ conn.getResponseMessage());
				System.exit(-1);
			} else {
				System.out.println(count++ + " ) POST method success on  "
						+ server_addr + " at ("
						+ (System.currentTimeMillis() - start) + ") for "
						+ sendToServer2.get(0).getInstrument().getSymbol()
						+ " (" + sendToServer2.size() + ")");
			}
			conn.getInputStream().close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		/*
		 * } });
		 */
	}

	private static Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/yuppiewall", "postgres",
				"adminadmin");
		connection.setAutoCommit(false);
		return connection;
	}

	public static void main(String[] args) {
		if (args[0] == null)
			return;
		System.out.println(args[0]);
		new CreateCacheClientV3(args[0]).start();

	}

}
