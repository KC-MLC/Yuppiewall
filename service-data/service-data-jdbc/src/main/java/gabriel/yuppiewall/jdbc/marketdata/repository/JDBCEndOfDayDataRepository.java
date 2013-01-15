package gabriel.yuppiewall.jdbc.marketdata.repository;

import gabriel.yuppiewall.common.Tupple;
import gabriel.yuppiewall.common.exception.InvalidParameterValueException;
import gabriel.yuppiewall.common.exception.MissingRequiredFiledException;
import gabriel.yuppiewall.indicator.TechnicalIndicator.SCAN_ON;
import gabriel.yuppiewall.indicator.domain.TechnicalIndicator_;
import gabriel.yuppiewall.indicator.trend.SimpleMovingAverage;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.GlobalFilter;
import gabriel.yuppiewall.scanner.domain.ScanParameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

@Service("JDBCEndOfDayDataRepository")
public class JDBCEndOfDayDataRepository implements EndOfDayDataRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void createEndOfDayData(EndOfDayData endOfDayData) {
		throw new UnsupportedOperationException(
				"not implemented>createEndOfDayData");
	}

	@Override
	public void createEndOfDayData(List<EndOfDayData> list) {
		throw new UnsupportedOperationException(
				"not implemented>createEndOfDayData");

	}

	@Override
	public Map<String, List<EndOfDayData>> findRecords(ScanParameter param) {
		// TODO only supporting two parameter from query should add
		// implementation for average function also
		GlobalFilter gfilter = param.getGlobalFilter();
		if (gfilter == null)
			throw new MissingRequiredFiledException(GlobalFilter.class,
					"globalFilter", "Missing Global Filter");
		Tupple<String, String> group = gfilter.getGroup();
		if (group == null)
			throw new MissingRequiredFiledException(GlobalFilter.class,
					"globalFilter", "Missing Global Filter");
		final List<EndOfDayData> list = new ArrayList<>();
		String key = group.getKey();
		String sql = null;

		if ("country".equals(key)) {
			sql = "select eod.identifier, eod.exchange, eod.symbol, eod.trade_date, eod.stock_volume, eod.stock_price_open, eod.stock_price_high, eod.stock_price_low, eod.stock_price_close, eod.stock_price_adj_close"
					+ " from end_of_day_data eod, exchange ex"
					+ " where eod.exchange = ex.ex_name and ex.ex_country_code=?"
					+ " ORDER BY eod.trade_date";
		} else if ("exchange".equals(key)) {
			sql = "select eod.identifier, eod.exchange, eod.symbol, eod.trade_date, eod.stock_volume, eod.stock_price_open, eod.stock_price_high, eod.stock_price_low, eod.stock_price_close, eod.stock_price_adj_close"
					+ " from end_of_day_data eod"
					+ " where eod.exchange = ?"
					+ " ORDER BY eod.trade_date";
		} else {
			throw new InvalidParameterValueException(GlobalFilter.class,
					"globalFilter", key + " Fileter Not supported");
		}
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
					list.add(new EndOfDayData(new Exchange(rs.getString(2)), rs
							.getString(3), new Date(rs.getDate(4).getTime()),
							rs.getBigDecimal(6), rs.getBigDecimal(7), rs
									.getBigDecimal(8), rs.getBigDecimal(9), rs
									.getBigDecimal(5), rs.getBigDecimal(10)));
				}

			}
		};
		executeStreamed(jdbcTemplate, callback, sql, group.getValue());
		// group them in symbol
		Map<String, List<EndOfDayData>> groupedValue = new HashMap<String, List<EndOfDayData>>();

		for (EndOfDayData eod : list) {

			List<EndOfDayData> eodList = groupedValue.get(eod.getStockSymbol());
			if (eodList == null) {
				eodList = new ArrayList<>();
				groupedValue.put(eod.getStockSymbol(), eodList);
			}
			eodList.add(eod);
		}

		Condition avePriceConition = gfilter.getAvgPrice();
		if (avePriceConition != null) {
			filter(avePriceConition, groupedValue, SCAN_ON.CLOSING);

		}
		Condition aveVolConition = gfilter.getAvgVolue();
		if (aveVolConition != null) {
			filter(aveVolConition, groupedValue, SCAN_ON.VOLUME);
		}
		return groupedValue;
	}

	private static void filter(Condition aveVolConition,
			Map<String, List<EndOfDayData>> groupedValue, SCAN_ON scanOn) {

		if (aveVolConition != null) {
			SimpleMovingAverage am = new SimpleMovingAverage();
			Iterator<String> itr = groupedValue.keySet().iterator();
			while (itr.hasNext()) {
				String symbol = itr.next();
				List<EndOfDayData> tempList = groupedValue.get(symbol);
				TechnicalIndicator_[] res = am.calculate(tempList,
						aveVolConition.getLhs().getValue().intValue(), scanOn);
				if (0 >= res[0].getValue().compareTo(
						aveVolConition.getRhs().getValue())) {
					itr.remove();
				}
			}

		}

	}

	private void executeStreamed(JdbcTemplate jdbcTemplate,
			PreparedStatementCallback callback, final String sql,
			final String value) {
		PreparedStatementCreator creator = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(sql,
						java.sql.ResultSet.TYPE_FORWARD_ONLY,
						java.sql.ResultSet.CONCUR_READ_ONLY);
				pstmt.setFetchSize(Integer.MIN_VALUE);
				pstmt.setString(1, value);
				return pstmt;
			}
		};
		jdbcTemplate.execute(creator, callback);
	}
}
