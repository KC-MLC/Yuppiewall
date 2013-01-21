package gabriel.yuppiewall.client;

import gabriel.yuppiewall.common.LineIterator;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InsertIntoSecurity {

	public static void insertRecord() throws SQLException,
			FileNotFoundException, ClassNotFoundException {
		// read and getsList of Instrument

		List<Instrument> instruments = getInstrument();

		// insert record
		String sql = "insert into instrument (symbol_code, comp_name, exchange) values (?, ?, ?)";
		Connection connection = getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);
		final int batchSize = 1000;
		int count = 0;
		for (Instrument instrument : instruments) {

			ps.setString(1, instrument.getSymbol());
			ps.setString(2, instrument.getName());
			ps.setString(3, instrument.getExchange().getName());
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

	private static List<Instrument> getInstrument()
			throws FileNotFoundException {
		//
		File file = new File("/home/parvez/Downloads/NASDAQ.txt");
		Iterator<String> itr = new LineIterator(new FileInputStream(file));
		List<Instrument> list = new ArrayList<Instrument>();

		while (itr.hasNext()) {
			String line = itr.next();

			String[] v = line.split(",");
			System.out.println("(" + list.size() + ") READING-->" + v[1]);
			list.add(new Instrument(v[1], new Exchange("nasdaq"), v[0], null,
					null, null));
		}
		return list;

	}

	public static void main(String[] args) throws FileNotFoundException,
			ClassNotFoundException, SQLException {
		insertRecord();
	}
}
