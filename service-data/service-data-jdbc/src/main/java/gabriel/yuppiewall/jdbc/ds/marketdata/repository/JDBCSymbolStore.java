package gabriel.yuppiewall.jdbc.ds.marketdata.repository;

import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;

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
	private  List<Instrument> symbolList;
	private  List<Server> serverList;

	public void init() {
		System.out.println("STARTTTTTTTTTTTTEDDDD  INNNNNIIIIIIIIIIIIIIT");
		String sql = "Select symbol_code, server_id, exchange from  instrument";

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
				symbolList = new ArrayList<Instrument>();
				while (rs.next()) {
					Instrument i = new Instrument(rs.getString(1),
							new Exchange(rs.getString(3)));
					i.setServer(rs.getString(2));
					symbolList.add(i);
				}

			}
		};

		executeStreamed(jdbcTemplate, callback, sql);
		symbolList = Collections.unmodifiableList(symbolList);
		System.out.println("STARTTTTTTTTTTTTEDDDD  INNNNNIIIIIIIIIIIIIIT OVEr");
		initSerever();
	}

	public void initSerever() {
		System.out.println("STARTTTTTTTTTTTTEDDDD  INNNNNIIIIIIIIIIIIIIT");
		String sql = "Select server_context, server_size from  region_server";

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
				serverList = new ArrayList<Server>();
				while (rs.next()) {
					serverList.add(new Server(rs.getString(1), rs.getInt(2)));
				}

			}
		};
		executeStreamed(jdbcTemplate, callback, sql);
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

	public  List<Instrument> getSymbolList() {
		return symbolList;
	}
	

	public  List<Server> getServerList() {
		return serverList;
	}

}
