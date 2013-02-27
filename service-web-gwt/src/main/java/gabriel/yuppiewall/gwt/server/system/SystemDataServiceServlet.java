package gabriel.yuppiewall.gwt.server.system;

import gabriel.yuppiewall.common.exception.BusinessException;
import gabriel.yuppiewall.gwt.common.system.SystemDataService;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SystemDataServiceServlet extends RemoteServiceServlet implements
		SystemDataService {
	private WebApplicationContext appContext;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		appContext = WebApplicationContextUtils
				.getWebApplicationContext(servletConfig.getServletContext());
	}

	@Override
	public List<Instrument> getAllInstrument(String query,
			String tradeCurrencyCode) {
		ArrayList<Instrument> retValue = new ArrayList<Instrument>();
		try {

			SystemDataRepository systemDataRepository = (SystemDataRepository) appContext
					.getBean("SystemDataRepository");

			Collection<Instrument> instruments = systemDataRepository
					.getInstruments();
			for (Instrument instrument : instruments) {
				String curCode = instrument.getExchange()
						.getTradeCurrencyCode();
				if (!curCode.equals(tradeCurrencyCode))
					continue;
				/*if (!instrument.getName().contains(query)) {

					continue;
				} else if (!instrument.getSymbol().contains(query)) {
					continue;
				}*/
				retValue.add(new Instrument(instrument.getSymbol(), instrument
						.getName()));
			}

		} catch (BusinessException be) {
			throw be;
		} catch (Exception e) {
			System.out.println("EROOOOOOOOOOOOO");
			e.printStackTrace();
		}
		return retValue;
	}

}
