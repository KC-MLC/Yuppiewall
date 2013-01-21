package gabriel.yuppiewall.jdbc.marketdata.repository;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.ScanRequest;
import gabriel.yuppiewall.scanner.domain.GlobalFilter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

@Service("JDBCInMemoryMarketdata")
public class JDBCInMemoryMarketdata extends JDBCEndOfDayDataRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private Map<Instrument, List<EndOfDayData>> groupedValue;

	public void init() {
		System.out.println("STARTTTTTTTTTTTTEDDDD  INNNNNIIIIIIIIIIIIIIT");
		String sql = "select eod.identifier, eod.exchange, eod.symbol, eod.trade_date, eod.stock_volume, eod.stock_price_open, eod.stock_price_high, eod.stock_price_low, eod.stock_price_close, eod.stock_price_adj_close"
				+ " from end_of_day_data eod, exchange ex"
				+ " where trade_date >? order by trade_date desc";

		final List<EndOfDayData> list = new ArrayList<>();
		PreparedStatementCallback callback = new PreparedStatementCallback() {
			@Override
			public Void doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				ResultSet rs = pstmt.executeQuery();
				extractData(rs, list);
				rs.close();
				return null;
			}

			private void extractData(ResultSet rs, List<EndOfDayData> list)
					throws SQLException {
				while (rs.next()) {
					System.out.println("processing " + rs.getString(1));

					list.add(new EndOfDayData(new Instrument(rs.getString(3),
							new Exchange(rs.getString(2))), new Date(rs
							.getDate(4).getTime()), rs.getBigDecimal(6), rs
							.getBigDecimal(7), rs.getBigDecimal(8), rs
							.getBigDecimal(9), rs.getBigDecimal(5), rs
							.getBigDecimal(10)));
				}

			}
		};
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			executeStreamed(jdbcTemplate, callback, sql,
					sdf.parse("2012-11-01"));
		} catch (ParseException ignore) {
			ignore.printStackTrace();
		}
		// group them in symbol
		HashMap<Instrument, List<EndOfDayData>> temp = new HashMap<>();

		for (EndOfDayData eod : list) {

			List<EndOfDayData> eodList = temp.get(eod.getInstrument());
			if (eodList == null) {
				eodList = new ArrayList<>();
				temp.put(eod.getInstrument(), eodList);
			}
			eodList.add(eod);
		}
		groupedValue = Collections.unmodifiableMap(temp);
		System.out.println("STARTTTTTTTTTTTTEDDDD  INNNNNIIIIIIIIIIIIIIT OVEr");
	}

	private void executeStreamed(JdbcTemplate jdbcTemplate,
			PreparedStatementCallback callback, final String sql,
			final Date value) {
		PreparedStatementCreator creator = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(sql,
						java.sql.ResultSet.TYPE_FORWARD_ONLY,
						java.sql.ResultSet.CONCUR_READ_ONLY);
				pstmt.setFetchSize(0);
				pstmt.setDate(1, (new java.sql.Date(value.getTime())));
				return pstmt;
			}
		};
		jdbcTemplate.execute(creator, callback);
	}

	protected ScanRequest createList(GlobalFilter gfilter) {

		return new ScanRequest(new LinkedList<>(groupedValue.keySet()),
				groupedValue);
	}
}
