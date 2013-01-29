package gabriel.yuppiewall.si.marketdata.service;

import gabriel.yuppiewall.market.service.MarketService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.EndOfDayDataRepository;
import gabriel.yuppiewall.marketdata.service.EndOfDayServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.SmartLifecycle;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component(value = "endOfDayDataReceiverSI")
public class EndOfDayDataReceiver extends EndOfDayServiceImpl implements
		SmartLifecycle {

	@Autowired
	@Qualifier("JPAEndOfDayDataRepository")
	private EndOfDayDataRepository endOfDayDataRepository;
	@Autowired
	@Qualifier("MarketService")
	private MarketService marketService;

	@ServiceActivator
	@Override
	public void saveEOD(EndOfDayData[] eod) {

		super.saveEOD(eod);

	}

	@Override
	protected EndOfDayDataRepository getEndOfDayDataRepository() {
		return endOfDayDataRepository;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		System.out
				.println("**********************************************************************************88");
	}

	@Override
	public void stop() {

	}

	@Override
	public int getPhase() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable arg0) {
		// TODO Auto-generated method stub

	}

}
