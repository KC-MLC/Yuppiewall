package gabriel.yuppiewall.vaadin.application.scanner;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.ScanOutput;

import java.io.Serializable;
import java.math.BigDecimal;

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
		ScanOutput[] scanResult = (ScanOutput[]) e.getSource();
		for (ScanOutput scanOutput : scanResult) {
			addRow(scanOutput);
		}

	}

	private void addRow(ScanOutput data) {
		Instrument ins = data.getInstrument();
		EndOfDayData eod = data.getEod();

		Item item = resultTable.addItem(ins.getSymbol());
		item.getItemProperty(SYMBOL).setValue(ins.getSymbol());
		item.getItemProperty(NAME).setValue("-");
		item.getItemProperty(EXCHANGE).setValue("-");
		item.getItemProperty(SECTOR).setValue("-");
		item.getItemProperty(INDUSTRY).setValue("-");
		item.getItemProperty(OPEN).setValue(eod.getStockPriceOpen());
		item.getItemProperty(HIGH).setValue(eod.getStockPriceHigh());
		item.getItemProperty(LOW).setValue(eod.getStockPriceLow());
		item.getItemProperty(CLOSE).setValue(eod.getStockPriceAdjClose());
		item.getItemProperty(VOLUME).setValue(eod.getStockVolume());

	}

	public com.vaadin.ui.Component getRoot() {
		return resultTable;
	}
}
