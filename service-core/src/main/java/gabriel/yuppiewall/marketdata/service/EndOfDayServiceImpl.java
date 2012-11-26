package gabriel.yuppiewall.marketdata.service;

import gabriel.yuppiewall.market.service.MarketService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData_;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;

public class EndOfDayServiceImpl implements EndOfDayService {

	@Override
	public void saveEOD(EndOfDayData_ eod) {
		// check if this is unique business day

		{// TODO check if this step is necessary
			getMarketService().createIfNotPresent(eod.getExchange(),
					eod.getDate());

		}

		getEndOfDayDataRepository().createEndOfDayData(eod);

	}

	private EndOfDayDataRepository getEndOfDayDataRepository() {
		return null;
		// TODO Auto-generated method stub

	}

	private MarketService getMarketService() {
		return null;
		// TODO Auto-generated method stub

	}

}
