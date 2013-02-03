package gabriel.yuppiewall.vaadin.application.scanner;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.ScanOutput;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.Item;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ScanResultViewImpl implements Serializable {

	private static final Object SELECT = ".";
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
	private VerticalLayout rootLayout;

	class ScanResultRecorder {
		@Subscribe
		public void recordScanResult(NewScanResult e) {

			loadGrid(e);
		}
	}

	@Autowired
	private EventBus eventBus;
	private Label resultStatus;

	public void init() {
		eventBus.register(new ScanResultRecorder());
		/*
		 * rootlayout = new VerticalLayout(); rootlayout.setSizeFull();
		 */
		rootLayout = new VerticalLayout();
		rootLayout.setSpacing(true);

		HorizontalLayout menuBar = new HorizontalLayout();
		rootLayout.addComponent(menuBar);
		menuBar.setSpacing(true);
		Button compare = new Button("Compare", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {

			}
		});
		compare.setDescription("Compare Selected Stocks");
		compare.setStyleName(BaseTheme.BUTTON_LINK);
		compare.setIcon(new ThemeResource(
				"../wall-midnight/icons/16/compare.png"));
		menuBar.addComponent(compare);

		menuBar.setComponentAlignment(compare, Alignment.MIDDLE_LEFT);

		final Button exportToExcel = new Button("Export");
		exportToExcel.setStyleName(BaseTheme.BUTTON_LINK);
		exportToExcel.setDescription("Export Result To Excel");
		exportToExcel.setIcon(new ThemeResource(
				"../runo/icons/16/document-xsl.png"));

		exportToExcel.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

			}
		});

		menuBar.addComponent(exportToExcel);
		menuBar.setComponentAlignment(exportToExcel, Alignment.MIDDLE_LEFT);

		resultStatus = new Label("Count: ");

		menuBar.addComponent(resultStatus);
		menuBar.setComponentAlignment(resultStatus, Alignment.MIDDLE_LEFT);

		resultTable = new Table();
		rootLayout.addComponent(resultTable);
		resultTable.setWidth("100%");
		resultTable.setColumnCollapsingAllowed(true);
		resultTable.setSelectable(true);
		resultTable.setMultiSelect(false);

		resultTable.addContainerProperty(SELECT, CheckBox.class, null);
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
		List<ScanOutput> scanResult = (List<ScanOutput>) e.getSource();
		for (ScanOutput scanOutput : scanResult) {
			addRow(scanOutput);
		}
		resultStatus.setValue("Count: " + scanResult.size());

	}

	private void addRow(ScanOutput data) {
		if (data == null)
			return;
		Instrument ins = data.getInstrument();
		EndOfDayData eod = data.getEod();

		Item item = resultTable.addItem(ins.getSymbol());
		CheckBox select = new CheckBox();
		select.setImmediate(true);

		select.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				boolean enabled = event.getButton().booleanValue();

			}
		});
		item.getItemProperty(SELECT).setValue(select);
		item.getItemProperty(SYMBOL).setValue(ins.getSymbol());
		item.getItemProperty(NAME).setValue(ins.getName());
		item.getItemProperty(EXCHANGE).setValue(ins.getExchange().getSymbol());
		item.getItemProperty(SECTOR).setValue("-");
		item.getItemProperty(INDUSTRY).setValue("-");
		item.getItemProperty(OPEN).setValue(eod.getStockPriceOpen());
		item.getItemProperty(HIGH).setValue(eod.getStockPriceHigh());
		item.getItemProperty(LOW).setValue(eod.getStockPriceLow());
		item.getItemProperty(CLOSE).setValue(eod.getStockPriceAdjClose());
		item.getItemProperty(VOLUME).setValue(eod.getStockVolume());

	}

	public com.vaadin.ui.Component getRoot() {
		return rootLayout;
	}
}
