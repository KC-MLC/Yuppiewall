package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.trade.domain.Portfolio;

public interface TransactionView {

	interface TransactionViewEvent {

		void updateView(Portfolio selectedPortfolio);

	}

	public void addListener(TransactionViewEvent eventHandeler);
}