package gabriel.yuppiewall.gwt.common.application.portfolio;

import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("portfolioService")
public interface PortfolioService extends RemoteService {

	AccountSummary[] getAccountSummaryCurrencyWise(PrimaryPrincipal subject);

}
