package gabriel.yuppiewall.ds.service;

import gabriel.yuppiewall.common.Command;
import gabriel.yuppiewall.ds.domain.Server;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.util.ArrayList;
import java.util.List;

public abstract class InsertIntoRegion {

	public void pushData() {

		final List<Server> serverList = getServer();
		validateServerList(serverList);

		List<Instrument> instruments = getAllInstruments();

		final int distribution = (int) Math.ceil(instruments.size()
				/ serverList.size());

		System.out.println("distribution==" + distribution);
		int index = 0;
		for (int i = 0; i < serverList.size(); i++) {
			Server server = serverList.get(i);

			for (int j = i * distribution, max = (((i + 1) * distribution) > instruments
					.size()) ? instruments.size() : (i + 1) * distribution; j < max; j++) {
				Instrument instrument = instruments.get(j);
				instrument.setServer(server.getServerContext());
			}
		}
		updateInstrumentServerDetails(instruments);

	}

	protected abstract void updateInstrumentServerDetails(
			List<Instrument> instruments);

	protected abstract List<Instrument> getAllInstruments();

	protected abstract void validateServerList(List<Server> serverList);

	protected abstract List<Server> getServer();
}
