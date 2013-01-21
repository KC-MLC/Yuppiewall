package gabriel.yuppiewall.jdbc.ds.marketdata.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

@Service("JDBCSymbolStore")
public class JDBCSymbolStore {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static List<String> symbolList;

	public void init() {
		System.out.println("STARTTTTTTTTTTTTEDDDD  INNNNNIIIIIIIIIIIIIIT");
		String sql = "select distinct symbol from end_of_day_data order by symbol";

		PreparedStatementCallback callback = new PreparedStatementCallback() {
			@Override
			public Void doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				ResultSet rs = pstmt.executeQuery();
				extractData(rs);
				rs.close();
				return null;
			}

			private void extractData(ResultSet rs) throws SQLException {
				symbolList = new ArrayList<>();
				while (rs.next()) {
					String symbol = rs.getString(1);
					System.out.println("processing " + symbol);
					symbolList.add(symbol);
				}

			}
		};

		executeStreamed(jdbcTemplate, callback, sql);

		symbolList = Collections.unmodifiableList(symbolList);
		System.out.println("STARTTTTTTTTTTTTEDDDD  INNNNNIIIIIIIIIIIIIIT OVEr");
	}

	private void executeStreamed(JdbcTemplate jdbcTemplate,
			PreparedStatementCallback callback, final String sql) {
		PreparedStatementCreator creator = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(sql,
						java.sql.ResultSet.TYPE_FORWARD_ONLY,
						java.sql.ResultSet.CONCUR_READ_ONLY);
				pstmt.setFetchSize(0);
				return pstmt;
			}
		};
		jdbcTemplate.execute(creator, callback);
	}

	public static List<String> getSymbolList() {
		return symbolList;
	}

}
