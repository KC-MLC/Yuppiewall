package gabriel.yuppiewall.workers.eod;

import gabriel.yuppiewall.market.domain.Exchange_;
import gabriel.yuppiewall.market.repository.MarketRepository;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;

public class EndOfDayDataService {

	public void postEndOfDayData(Exchange_ exchange, EndOfDayData_ eod) {

		// getMarketRepository().getLastTradingDate();
	}

	private MarketRepository getMarketRepository() {
		return null;
		// TODO Auto-generated method stub

	}
}
