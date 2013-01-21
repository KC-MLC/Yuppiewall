package gabriel.yuppiewall.client;

import gabriel.yuppiewall.common.Command;
import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.ds.service.InsertIntoRegion;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateCacheClient extends InsertIntoRegion {

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
			pstmt.setFetchSize(0);

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

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	@Override
	protected void updateDataList(List<EndOfDayData> dataList, Server server) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateServer(List<EndOfDayData> dataList, Server server) {
		// TODO Auto-generated method stub

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
		new CreateCacheClient().pushData();
	}

}
