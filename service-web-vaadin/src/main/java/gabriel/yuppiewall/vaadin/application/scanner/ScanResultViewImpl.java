package gabriel.yuppiewall.vaadin.application.scanner;

import gabriel.yuppiewall.marketdata.domain.EndOfDayData;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.Item;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ScanResultViewImpl implements Serializable {

	private static final Object SYMBOL = "Symbol";
	private static final Object NAME = "Name";
	private static final Object EXCHANGE = "Exchange";
	private static final Object SECTOR = "Sector";
	private static final Object INDUSTRY = "Industry";
	private static final Object OPEN = "OPEN";
	private static final Object HIGH = "High";
	private static final Object LOW = "Low";
	private static final Object CLOSE = "Close";
	private static final Object VOLUME = "Volume";

	private Table resultTable;

	class ScanResultRecorder {
		@Subscribe
		public void recordScanResult(NewScanResult e) {

			loadGrid(e);
		}
	}

	@Autowired
	private EventBus eventBus;

	public void init() {
		eventBus.register(new ScanResultRecorder());
		/*
		 * rootlayout = new VerticalLayout(); rootlayout.setSizeFull();
		 */

		resultTable = new Table();
		resultTable.setWidth("100%");
		resultTable.setColumnCollapsingAllowed(true);
		resultTable.setSelectable(true);
		resultTable.setMultiSelect(false);

		resultTable.addContainerProperty(SYMBOL, String.class, "");
		resultTable.addContainerProperty(NAME, String.class, "");
		resultTable.addContainerProperty(EXCHANGE, String.class, "");
		resultTable.addContainerProperty(SECTOR, String.class, "");
		resultTable.addContainerProperty(INDUSTRY, String.class, "");

		resultTable.addContainerProperty(OPEN, BigDecimal.class,
				new BigDecimal(0.0));
		resultTable.addContainerProperty(HIGH, BigDecimal.class,
				new BigDecimal(0.0));
		resultTable.addContainerProperty(LOW, BigDecimal.class, new BigDecimal(
				0.0));
		resultTable.addContainerProperty(CLOSE, BigDecimal.class,
				new BigDecimal(0.0));
		resultTable.addContainerProperty(VOLUME, BigDecimal.class,
				new BigDecimal(0.0));

	}

	public void loadGrid(NewScanResult e) {
		resultTable.removeAllItems();
		@SuppressWarnings("unchecked")
		List<EndOfDayData> scanResult = (List<EndOfDayData>) e.getSource();
		for (EndOfDayData endOfDayData_ : scanResult) {
			addRow(endOfDayData_);
		}

	}

	private void addRow(EndOfDayData data) {
		Item item = resultTable.addItem(data.getStockSymbol());
		item.getItemProperty(SYMBOL).setValue(data.getStockSymbol());
		item.getItemProperty(NAME).setValue("-");
		item.getItemProperty(EXCHANGE).setValue("-");
		item.getItemProperty(SECTOR).setValue("-");
		item.getItemProperty(INDUSTRY).setValue("-");
		item.getItemProperty(OPEN).setValue(data.getStockPriceOpen());
		item.getItemProperty(HIGH).setValue(data.getStockPriceHigh());
		item.getItemProperty(LOW).setValue(data.getStockPriceLow());
		item.getItemProperty(CLOSE).setValue(data.getStockPriceAdjClose());
		item.getItemProperty(VOLUME).setValue(data.getStockVolume());

	}

	public com.vaadin.ui.Component getRoot() {
		return resultTable;
	}
}
