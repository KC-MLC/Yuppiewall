package gabriel.yuppiewall.vaadin.application.portfolio;

import java.io.Serializable;
import java.util.List;

import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.service.PortfolioService;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;
import gabriel.yuppiewall.vaadin.YuppiewallUI;

@SuppressWarnings("serial")
public class PortfolioTreeViewPresenter implements
		PortfolioTreeView.PortfolioTreeViewEvent, Serializable {

	@Override
	public List<Portfolio> updateTreeView() {
		PortfolioService ps = YuppiewallUI.getInstance().getService(
				"portfolioService");
		List<Portfolio> list = ps.getPortfolio((PrimaryPrincipal) YuppiewallUI
				.getInstance().getApplicationData("user"));
		return list;

	}
}
