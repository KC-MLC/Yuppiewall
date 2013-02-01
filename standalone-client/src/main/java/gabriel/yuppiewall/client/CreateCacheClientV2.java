package gabriel.yuppiewall.client;

import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.ds.service.InsertIntoRegion;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CreateCacheClientV2 extends InsertIntoRegion {
	@Override
	protected void updateInstrumentServerDetails(List<Instrument> instruments) {

		String sql = "UPDATE instrument"
				+ " SET server_id=? where symbol_code=?";
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			final int batchSize = 1001;
			int count = 0;
			for (Instrument instrument : instruments) {

				ps.setString(1, instrument.getServer());
				ps.setString(2, instrument.getSymbol());

				System.out.println("(" + count + ") INSERTING-->"
						+ instrument.getSymbol());
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
	protected List<Instrument> getAllInstruments() {
		try {
			String sql = "Select symbol_code, server_id, exchange from  instrument order by exchange";
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
