package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.trade.domain.Portfolio;

import java.util.List;

public interface PortfolioTreeView {

	public static final Object PORTFOLIO_PROPERTY_NAME = "name";
	public static final Object PORTFOLIO_PROPERTY_VALUE = "value";
	public static final Object PORTFOLIO_PROPERTY_ICON = "icon";

	void updateTreeView(List<Portfolio> portfolio);
}
