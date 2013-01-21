package gabriel.yuppiewall.client;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.service.EndOfDayService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EndOfDayServiceJDBCClient implements EndOfDayService {
	static int count = 0;

	public void saveEOD(EndOfDayData[] eodList) {
		try {
			String sql = "INSERT INTO end_of_day_data("
					+ " identifier, trade_date, stock_price_adj_close, stock_price_close,"
					+ " stock_price_high, stock_price_low, stock_price_open, stock_volume,"
					+ " instrument) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			final int batchSize = 1001;

			for (EndOfDayData eod : eodList) {

				String identifier = eod.getInstrument().getExchange().getName()
						+ eod.getInstrument().getSymbol() + eod.getStrDate();

				ps.setString(1, identifier);
				ps.setDate(2, new java.sql.Date(eod.getDate().getTime()));
				ps.setBigDecimal(3, eod.getStockPriceAdjClose());
				ps.setBigDecimal(4, eod.getStockPriceClose());
				ps.setBigDecimal(5, eod.getStockPriceHigh());
				ps.setBigDecimal(6, eod.getStockPriceLow());
				ps.setBigDecimal(7, eod.getStockPriceOpen());
				ps.setBigDecimal(8, eod.getStockVolume());
				ps.setString(9, eod.getInstrument().getSymbol());

				System.out.println("(" + count + ") INSERTING-->" + identifier);
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
		}

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

}
