package gabriel.yuppiewall.client;

import gabriel.yuppiewall.common.Command;
import gabriel.yuppiewall.common.Tupple;
import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.ds.service.InsertIntoRegion;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.codehaus.jackson.map.ObjectMapper;

public class CreateCacheClientV2 extends InsertIntoRegion {

	private static Proxy SYSTEM_PROXY;
	// static ExecutorService executo = Executors.newFixedThreadPool(20);
	//HttpHost proxy = new HttpHost("43.80.41.41", 8080, "http");
	private Map<Server, ExecutorService> serverToThread = new HashMap<Server, ExecutorService>();
	private ObjectMapper mapper = new ObjectMapper();
	private Set<Tupple<Instrument, Server>> updateDataList = new HashSet<Tupple<Instrument, Server>>();
	Map<Server, List<List<EndOfDayData>>> sendToServer = new HashMap<Server, List<List<EndOfDayData>>>();
	private PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
	{
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(5);
	}
	DefaultHttpClient httpclient = new DefaultHttpClient(cm);
	{

		/*httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);*/
	}

	@Override
	protected void onData(Command<EndOfDayData> command) {
		try {
			String sql = "SELECT identifier, trade_date, stock_price_adj_close, stock_price_close,"
					+ " stock_price_high, stock_price_low, stock_price_open, stock_volume,"
					+ " instrument FROM end_of_day_data ORDER BY instrument, trade_date DESC";
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql,
					java.sql.ResultSet.TYPE_FORWARD_ONLY,
					java.sql.ResultSet.CONCUR_READ_ONLY);
			pstmt.setFetchSize(1000);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				EndOfDayData eod = new EndOfDayData(new Instrument(
						rs.getString(9)), new Date(rs.getDate(2).getTime()),
						rs.getBigDecimal(7), rs.getBigDecimal(5),
						rs.getBigDecimal(6), rs.getBigDecimal(4),
						rs.getBigDecimal(8), rs.getBigDecimal(3));
				command.execute(eod);
			}
			command.execute(null);
			rs.close();
			pstmt.close();
			con.close();

			sendToServer(sendToServer);
			updateDataList(updateDataList);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	private void sendToServer(
			Map<Server, List<List<EndOfDayData>>> sendToServer2) {

		System.out.println("CREATED POOL OF " + sendToServer2.size());
		Iterator<Server> itr = sendToServer2.keySet().iterator();
		while (itr.hasNext()) {
			final Server server = itr.next();
			final List<List<EndOfDayData>> list = sendToServer2.get(server);

			new Thread(new Runnable() {

				public void run() {

					for (List<EndOfDayData> eodList : list) {

						if (eodList == null || eodList.size() == 0) {
							System.out
									.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
						}
						System.out.println("Sending (" + eodList.size()
								+ ") To " + server);
						sendToServer(eodList, server,
								serverToThread.get(server));
					}

				}

			}).start();
		}

	}

	private void updateDataList(Set<Tupple<Instrument, Server>> updateDataList2) {
		String sql = "UPDATE instrument"
				+ " SET server_id=? where symbol_code=?";
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			final int batchSize = 1001;
			int count = 0;
			for (Tupple<Instrument, Server> tupple : updateDataList2) {

				ps.setString(1, tupple.getValue().getServerContext());
				ps.setString(2, tupple.getKey().getSymbol());

				System.out.println("(" + count + ") INSERTING-->"
						+ tupple.getKey().getSymbol());
				ps.addBatch();

				if (++count % batchSize == 0) {
					ps.executeBatch();
					connection.commit();
				}
			}
			ps.executeBatch(); // insert remaining records
			connection.commit();
			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	@Override
	protected void updateDataList(Instrument symbol, Server server) {
		updateDataList.add(new Tupple<Instrument, Server>(symbol, server));
		System.out.println("(" + updateDataList.size() + ") " + symbol);

	}

	static {
		// System.setProperty("java.net.useSystemProxies", "true");
		SYSTEM_PROXY = Proxy.NO_PROXY;
		/*
		 * SYSTEM_PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
		 * "43.80.41.41", 8080));
		 */
	}

	@Override
	protected void updateServer(final List<EndOfDayData> dataList,
			final Server server) {
		List<List<EndOfDayData>> list = sendToServer.get(server);
		if (list == null) {
			list = new ArrayList<List<EndOfDayData>>();
			sendToServer.put(server, list);
			serverToThread.put(server, Executors.newFixedThreadPool(5));
		}
		list.add(dataList);
	}

	private void sendToServer(final List<EndOfDayData> dataList,
			final Server server, ExecutorService executo) {

		executo.submit(new Runnable() {

			public void run() {

				// TODO Auto-generated method stub
				HttpPut httpPut = new HttpPut(server.getServerContext()
						+ "/cache/");
				try {

					String jsonValue = mapper.writeValueAsString(dataList);
					StringEntity entity = new StringEntity(jsonValue);
					entity.setContentType("application/json");
					httpPut.setEntity(entity);
					long start = System.currentTimeMillis();
					HttpResponse response = httpclient.execute(httpPut,
							new BasicHttpContext());
					int code = response.getStatusLine().getStatusCode();
					if (code != HttpURLConnection.HTTP_ACCEPTED) {
						System.out.println("PUT method failed: " + code);
						InputStream is = response.getEntity().getContent();
						StringWriter writer = new StringWriter();
						IOUtils.copy(is, writer);
						String theString = writer.toString();
						System.out.println(theString);
						System.exit(-1);
					} else {
						System.out.println("POST method success on  " + server
								+ " at ("
								+ (System.currentTimeMillis() - start) + ")");

					}

					/*
					 * URL url = new URL(server.getServerContext() + "/cache/");
					 * HttpURLConnection conn = (HttpURLConnection) url
					 * .openConnection(SYSTEM_PROXY);
					 * conn.setRequestMethod("PUT"); conn.setDoOutput(true);
					 * conn.setUseCaches(false);
					 * conn.setRequestProperty("Content-Type",
					 * "application/json"); conn.setRequestProperty("Accept",
					 * "application/json"); String jsonValue =
					 * mapper.writeValueAsString(dataList);
					 * conn.setRequestProperty("Content-Length",
					 * Integer.toString(jsonValue.length())); long start =
					 * System.currentTimeMillis(); System.out.println("sending "
					 * + jsonValue.length());
					 * conn.getOutputStream().write(jsonValue.getBytes());
					 * conn.getOutputStream().flush();
					 * conn.getOutputStream().close(); conn.connect();
					 * 
					 * if (conn.getResponseCode() !=
					 * HttpURLConnection.HTTP_ACCEPTED) {
					 * System.out.println("PUT method failed: " +
					 * conn.getResponseCode() + "\t" +
					 * conn.getResponseMessage()); System.exit(-1); } else {
					 * System.out.println("POST method success on  " + server +
					 * " at (" + (System.currentTimeMillis() - start) + ")"); }
					 * conn.getInputStream().close(); conn.disconnect();
					 */
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				} finally {
					httpPut.releaseConnection();
				}

			}
		});

	}

	@Override
	protected List<Instrument> getAllInstruments() {
		try {
			String sql = "Select symbol_code, server_id, exchange from  instrument";
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List<Instrument> instruments = new ArrayList<Instrument>();
			while (rs.next()) {
				Instrument i = new Instrument(rs.getString(1), new Exchange(
						rs.getString(3)));
				i.setServer(rs.getString(2));
				instruments.add(i);
			}
			rs.close();
			stmt.close();
			con.close();
			return instruments;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}

	@Override
	protected void validateServerList(List<Server> serverList) {
		// TODO need to ping the server

	}

	@Override
	protected List<Server> getServer() {
		try {
			String sql = "Select server_context, server_size from  region_server";
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List<Server> servers = new ArrayList<Server>();
			while (rs.next()) {
				servers.add(new Server(rs.getString(1), rs.getInt(2)));
			}
			rs.close();
			stmt.close();
			con.close();
			return servers;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
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
		new CreateCacheClientV2().pushData();
	}

}
