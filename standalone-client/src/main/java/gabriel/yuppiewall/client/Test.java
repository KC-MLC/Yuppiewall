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

public class Test {

	public static void insertRecord() throws SQLException,
			FileNotFoundException, ClassNotFoundException {
		// read and getsList of Instrument

		List<String> symbols = getInstrument();

		// insert record
		String sql = "delete from instrument where symbol_code = ?";
		Connection connection = getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);
		final int batchSize = 10;
		int count = 0;
		for (String symbol : symbols) {

			ps.setString(1, symbol);

			System.out.println("(" + count + ") DELETING-->" + symbol);
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

	private static List<String> getInstrument() throws FileNotFoundException {
		//
		File file = new File("/home/parvez/Downloads/todell.txt");
		Iterator<String> itr = new LineIterator(new FileInputStream(file));
		List<String> list = new ArrayList<String>();

		while (itr.hasNext()) {
			String line = itr.next();

			System.out.println("(" + list.size() + ") READING-->" + line);
			list.add(line);
		}
		return list;

	}

	public static void main(String[] args) throws FileNotFoundException,
			ClassNotFoundException, SQLException {
		insertRecord();
	}
}
