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

		onData(

		new Command<EndOfDayData>() {
			private List<EndOfDayData> dataList;
			private Instrument symbol;
			int count;
			int serverIndex;

			@Override
			public void execute(EndOfDayData data) {
				if (data == null) {
					sendDataToServer();
					return;
				}
				if (symbol == null) {
					symbol = data.getInstrument();
					dataList = new ArrayList<>();
				}
				if (symbol.equals(data.getInstrument())) {
					dataList.add(data);
					return;
				} else {
					sendDataToServer();
					symbol = null;
					dataList = null;
				}

			}

			private void sendDataToServer() {
				if (dataList == null)
					return;
				if (count > distribution) {
					count = 0;
					serverIndex += 1;
				}
				Server server = serverList.get(serverIndex);
				count += 1;
				updateServer(dataList, server);
				updateDataList(dataList, server);

			}

		});

	}

	protected abstract void onData(Command<EndOfDayData> command);

	protected abstract void updateDataList(List<EndOfDayData> dataList,
			Server server);

	protected abstract void updateServer(List<EndOfDayData> dataList,
			Server server);

	protected abstract List<Instrument> getAllInstruments();

	protected abstract void validateServerList(List<Server> serverList);

	protected abstract List<Server> getServer();
}
