package gabriel.yuppiewall.gwt.common.system;

import gabriel.yuppiewall.instrument.domain.Instrument;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("systemDataService")
public interface SystemDataService extends RemoteService {

	List<Instrument> getAllInstrument(String query, String tradeCurrencyCode);

}
