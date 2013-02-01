package gabriel.yuppiewall.jdbc.marketdata.repository;

import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.market.domain.Exchange;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

@Service("JDBCSystemDataRepository")
public class JDBCSystemDataRepository implements SystemDataRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<String, Instrument> instruments = new HashMap<>();
	private Map<String, Exchange> exchanges = new HashMap<>();
	private Map<String, Server> servers = new HashMap<>();
	private Map<Exchange, Set<Server>> exchangeServerList = new HashMap<>();
	private Map<Exchange, List<Instrument>> exchangeInstrumentList = new HashMap<>();
	private Map<String, List<Exchange>> exchangeCountryList = new HashMap<>();

	public void init() {
		{
			String sql = "SELECT symbol_code, industry, comp_name, sector, server_id, exchange FROM instrument";
			PreparedStatementCallback callback = populateInstrument();

			executeStreamed(jdbcTemplate, callback, sql);
		}

		{
			String sql = "SELECT ex_symbol, ex_name, ex_country_code, ex_time_zone, ex_close_schedule FROM exchange;";
			PreparedStatementCallback callback = populateExchange();

			executeStreamed(jdbcTemplate, callback, sql);
		}
		{
			String sql = "SELECT server_context, server_size FROM region_server;";
			PreparedStatementCallback callback = populateServer();

			executeStreamed(jdbcTemplate, callback, sql);
		}
		// now poulate exchanges and servers
		Iterator<Instrument> itr = instruments.values().iterator();
		while (itr.hasNext()) {
			Instrument inst = itr.next();
			Exchange ex = inst.getExchange();
			Set<Server> setServer = exchangeServerList.get(ex);
			if (setServer == null) {
				setServer = new HashSet<Server>();
				exchangeServerList.put(ex, setServer);
			}
			setServer.add(servers.get(inst.getServer()));
		}
		// now poulate exchanges and Instrument
		itr = instruments.values().iterator();
		while (itr.hasNext()) {
			Instrument inst = itr.next();
			Exchange ex = inst.getExchange();
			List<Instrument> listInstrument = exchangeInstrumentList.get(ex);
			if (listInstrument == null) {
				listInstrument = new ArrayList<Instrument>();
				exchangeInstrumentList.put(ex, listInstrument);
			}
			listInstrument.add(inst);
		}
		// now poulate exchanges and Country

		Iterator<Exchange> exItr = exchangeInstrumentList.keySet().iterator();
		while (exItr.hasNext()) {
			Exchange ex = exItr.next();

			List<Exchange> listEx = exchangeCountryList.get(ex.getCountry());
			if (listEx == null) {
				listEx = new ArrayList<Exchange>();
				exchangeCountryList.put(ex.getCountry(), listEx);
			}
			listEx.add(ex);
		}

		System.out.println("STARTTTTTTTTTTTTEDDDD  INNNNNIIIIIIIIIIIIIIT OVEr");
	}

	private PreparedStatementCallback populateServer() {

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
				while (rs.next()) {
					// System.out.println("processing " + rs.getString(1));
					Server ex = getServer(rs.getString(1));
					ex.setSize(rs.getInt(2));
				}
			}

		};
		return callback;
	}

	private PreparedStatementCallback populateExchange() {

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
				while (rs.next()) {
					// System.out.println("processing " + rs.getString(1));
					Exchange ex = getExchange(rs.getString(1));
					ex.setCountry(rs.getString(3));
					ex.setName1(rs.getString(2));
					ex.setTimeZone(rs.getString(4));
					ex.setMarketCloseSchedule(rs.getString(5));
				}

			}

		};
		return callback;
	}

	private Exchange getExchange(String symbol) {
		Exchange ex = exchanges.get(symbol);
		if (ex == null) {
			ex = new Exchange(symbol);
			exchanges.put(symbol, ex);
		}
		return ex;
	}

	private Server getServer(String address) {
		Server server = servers.get(address);
		if (server == null) {
			server = new Server(address);
			servers.put(address, server);
		}
		return server;
	}

	private PreparedStatementCallback populateInstrument() {

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
				while (rs.next()) {
					// System.out.println("processing " + rs.getString(1));
					instruments.put(
							rs.getString(1),
							new Instrument(rs.getString(1), getExchange(rs
									.getString(6)), rs.getString(3), rs
									.getString(4), getServer(rs.getString(5))
									.getServerContext(), rs.getString(2)));

				}

			}

		};
		return callback;
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

	@Override
	public List<Exchange> getExchangeByCountryCode(String country) {

		return exchangeCountryList.get(country);
	}

	@Override
	public Collection<Server> getServerList() {
		return servers.values();
	}

	@Override
	public Set<Server> getExchangeServerList(Exchange exchange) {
		return exchangeServerList.get(exchange);
	}

	@Override
	public Exchange getExchange(Instrument instrument) {
		return exchanges.get(instruments.get(instrument.getSymbol())
				.getExchange().getSymbol());
	}

	@Override
	public LinkedList<Instrument> getInstrumentFromExchange(
			Set<String> exchanges) {
		LinkedList<Instrument> list = new LinkedList<>();
		for (String symbol : exchanges) {
			List<Instrument> inst = exchangeInstrumentList.get(new Exchange(
					symbol));
			list.addAll(inst);
		}
		return list;
	}

	@Override
	public Exchange getExchange(Exchange exchange) {
		return exchanges.get(exchange.getSymbol());
	}

	@Override
	public List<Instrument> getManagedInstrument(Server server) {
		List<Instrument> list = new ArrayList<>();
		String serverContext = server.getServerContext();
		Iterator<Instrument> itr = instruments.values().iterator();
		while (itr.hasNext()) {
			Instrument inst = itr.next();
			if (inst.getServer().equals(serverContext)) {
				list.add(new Instrument(inst.getSymbol(), new Exchange(inst
						.getExchange().getSymbol())));
			}
		}
		return list;
	}

	@Override
	public void setRegionIdentiy(String regionID) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Instrument getInstrument(Instrument inst) {
		return new Instrument(instruments.get(inst.getSymbol()));
	}
}
