package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.common.exception.BusinessException;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Order;
import gabriel.yuppiewall.trade.domain.Order.TransactionType;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.trade.service.AccountManager;
import gabriel.yuppiewall.vaadin.YuppiewallUI;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.henrik.drawer.Drawer;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class TransactionViewImpl implements TransactionView, Serializable {

	private static final Object TYPE_PROPERTY_NAME = "name";
	private static final Object TYPE_PROPERTY_VALUE = "value";
	private static final Object TT_SYMBOL = "Symbol";
	private static final Object TT_NAME = "Name";
	private static final Object TT_LAST_PRICE = "Last Price";
	private static final Object TT_SHARE = "Share";
	private static final Object TT_PRICE_SHARE = "Price/Share";
	private static final Object TT_MKT_VALUE = "Mkt Value";
	private static final Object TT_UN_REALIZED_PL = "Un realized P/L";
	private static final Object TT_UN_REALIZED_PL_PERCENT = "Un realized P/L %";
	private static final Object TT_REALIZED_PL = "Realized P/L";
	private static final Object TT_REALIZED_PL_PERCENT = "Realized P/L %";
	private Portfolio selectedPortfolio;
	private Label errorMessage = new Label();
	private VerticalLayout rootlayout;

	class PortfolioChangeRecorder {
		@Subscribe
		public void recordCustomerChange(PortfolioSelectedEvent e) {
			selectedPortfolio = (Portfolio) e.getSource();
			loadGrid();
		}
	}

	@Autowired
	private EventBus eventBus;
	private TreeTable holdingTT;

	public TransactionViewImpl() {
	}

	public void loadGrid() {
		holdingTT.removeAllItems();
		AccountManager accountManager = YuppiewallUI.getInstance().getService(
				"accountManager");
		List<Transaction> list = accountManager
				.getTransactions(selectedPortfolio);
		// group the list
		Map<Instrument, List<Transaction>> groupedMap = new HashMap<>();

		for (Transaction transaction : list) {
			List<Transaction> txList = groupedMap.get(transaction
					.getInstrument());
			if (txList == null) {
				txList = new ArrayList<>();
				groupedMap.put(transaction.getInstrument(), txList);
			}
			txList.add(transaction);
		}
		for (Iterator<Instrument> iterator = groupedMap.keySet().iterator(); iterator
				.hasNext();) {
			Instrument instrument = iterator.next();
			List<Transaction> txList = groupedMap.get(instrument);
			if (txList.size() > 1) {
				// create root
				setParentChild(instrument, txList);
			} else {
				Transaction tx = txList.get(0);
				addRow(tx, false);

			}

		}

	}

	private void addRow(Transaction tx, boolean allowChildren) {
		Item item = holdingTT.addItem(tx.getTransactionId());
		item.getItemProperty(TT_SYMBOL)
				.setValue(tx.getInstrument().getSymbol());
		item.getItemProperty(TT_SHARE).setValue(tx.getQuantity());
		item.getItemProperty(TT_PRICE_SHARE).setValue(tx.getPrice());
		holdingTT.setChildrenAllowed(tx.getTransactionId(), allowChildren);

	}

	private void setParentChild(Instrument instrument, List<Transaction> txList) {
		// TODO Auto-generated method stub

	}

	public void init() {
		eventBus.register(new PortfolioChangeRecorder());
		rootlayout = new VerticalLayout();
		rootlayout.setSizeFull();
		rootlayout.setMargin(true);
		rootlayout.setSpacing(true);
		// | | Edit portfolio | Delete
		// portfolio | Download to spreadsheet | Download to OFX

		HorizontalLayout butBar = new HorizontalLayout();
		butBar.setStyleName("small-segment");
		rootlayout.addComponent(butBar);
		{
			Button addNewPortfolio = new Button("Import transactions");
			addNewPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
			addNewPortfolio.setDescription("Import transactions");

			butBar.addComponent(addNewPortfolio);
		}
		{
			Button addNewPortfolio = new Button("Edit transactions");
			addNewPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
			addNewPortfolio.setDescription("Edit transactions");

			butBar.addComponent(addNewPortfolio);
		}
		{
			Button addNewPortfolio = new Button("Download to spreadsheet");
			addNewPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
			addNewPortfolio.setDescription("Download to spreadsheet");

			butBar.addComponent(addNewPortfolio);
		}
		{
			Button addNewPortfolio = new Button("Download to OFX");
			addNewPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
			addNewPortfolio.setDescription("Download to OFX");

			butBar.addComponent(addNewPortfolio);
		}

		holdingTT = new TreeTable();
		holdingTT.setWidth("100%");
		holdingTT.setColumnCollapsingAllowed(true);
		holdingTT.setSelectable(true);
		holdingTT.setMultiSelect(false);

		Panel holdingTTPanel = new Panel();
		holdingTTPanel.setSizeFull();
		holdingTTPanel.addComponent(holdingTT);
		rootlayout.addComponent(holdingTTPanel);
		rootlayout.setExpandRatio(holdingTTPanel, 1);

		holdingTT.addContainerProperty(TT_SYMBOL, String.class, "");
		holdingTT.addContainerProperty(TT_NAME, String.class, "");
		holdingTT.addContainerProperty(TT_LAST_PRICE, BigDecimal.class,
				new BigDecimal(0.0));
		holdingTT.addContainerProperty(TT_SHARE, Integer.class, new Integer(0));
		holdingTT.addContainerProperty(TT_PRICE_SHARE, BigDecimal.class,
				new BigDecimal(0.0));
		holdingTT.addContainerProperty(TT_MKT_VALUE, BigDecimal.class,
				new BigDecimal(0.0));
		holdingTT.addContainerProperty(TT_UN_REALIZED_PL, BigDecimal.class,
				new BigDecimal(0.0));
		holdingTT.addContainerProperty(TT_UN_REALIZED_PL_PERCENT,
				BigDecimal.class, new BigDecimal(0.0));
		holdingTT.addContainerProperty(TT_REALIZED_PL, BigDecimal.class,
				new BigDecimal(0.0));
		holdingTT.addContainerProperty(TT_REALIZED_PL_PERCENT,
				BigDecimal.class, new BigDecimal(0.0));

		VerticalLayout bottomLayount = new VerticalLayout();
		bottomLayount.setSpacing(true);

		Panel bottom = new Panel(bottomLayount);
		final Drawer drawer = new Drawer("Add Transaction", bottom);
		// Drawer is designed to work best with explicitly defined widths.
		drawer.setWidth("100%");
		rootlayout.addComponent(drawer);
		HorizontalLayout addNewTransactionLayout = new HorizontalLayout();
		bottom.addComponent(addNewTransactionLayout);
		// contentPaneSearchSection.addComponent(bottom);
		addNewTransactionLayout.setSpacing(true);

		IndexedContainer typeContainer = new IndexedContainer();
		typeContainer.addContainerProperty(TYPE_PROPERTY_NAME, String.class,
				null);
		typeContainer.addContainerProperty(TYPE_PROPERTY_VALUE,
				TransactionType.class, null);
		final ComboBox type = new ComboBox("Type", typeContainer);
		addNewTransactionLayout.addComponent(type);

		// Sets the combobox to show a certain property as the item caption
		type.setItemCaptionPropertyId(TYPE_PROPERTY_NAME);
		type.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);

		// Set a reasonable width
		type.setWidth(12, VerticalLayout.UNITS_EM);

		// Set the appropriate filtering mode for this example
		type.setFilteringMode(Filtering.FILTERINGMODE_STARTSWITH);
		type.setImmediate(true);

		// Disallow null selections
		type.setNullSelectionAllowed(false);
		setTypeContainer(typeContainer);

		final PopupDateField date = new PopupDateField("Date");
		addNewTransactionLayout.addComponent(date);
		date.setResolution(PopupDateField.RESOLUTION_DAY);

		final TextField share = new TextField("Symbol");
		addNewTransactionLayout.addComponent(share);

		final TextField price = new TextField("price");
		addNewTransactionLayout.addComponent(price);

		final TextField quantity = new TextField("Quantity");
		addNewTransactionLayout.addComponent(quantity);
		Button addNewTransaction = new Button("Add to portfolio",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						errorMessage.setValue("");
						String symbol = (String) share.getValue();
						Integer index = (Integer) type.getValue();
						TransactionType txType = TransactionType.getType(index);
						Object txDate = date.getValue();
						if (txDate == null || !(txDate instanceof Date)) {
							// getWindow().showNotification("Invalid date entered");
						}

						BigDecimal txPrice = new BigDecimal((String) price
								.getValue());

						Long txQuantity = Long.valueOf((String) quantity
								.getValue());

						AccountManager accountManager = YuppiewallUI
								.getInstance().getService("accountManager");
						Order order = new Order(txType, (Date) txDate,
								txQuantity, txPrice, null,
								new Instrument(symbol));
						try {
							accountManager.placeOrder(null, selectedPortfolio,
									order);
							YuppiewallUI.getInstance().uiController
									.showNotification("System Message",
											"Transaction Advice received ",
											Notification.TYPE_TRAY_NOTIFICATION);
							// eventBus.post(new ReloadTransactionEvent());
							loadGrid();
						} catch (BusinessException exception) {
							exception.printStackTrace();
							errorMessage.setValue(exception.getMessage());
						}

					}
				});
		bottom.addComponent(errorMessage);
		bottom.addComponent(addNewTransaction);

	}

	private void setTypeContainer(IndexedContainer container) {

		setTypeContainer(container, "Buy", TransactionType.BUY);
		setTypeContainer(container, "Sell", TransactionType.SELL);
		setTypeContainer(container, "Buy to Cover",
				TransactionType.BUY_TO_COVER);
		setTypeContainer(container, "Sell Short", TransactionType.SELL_SHORT);

	}

	public void onLoad() {

	}

	private void setTypeContainer(IndexedContainer container, String name,
			TransactionType type) {

		int id = type.getCode();
		Item item = container.addItem(id);
		item.getItemProperty(TYPE_PROPERTY_NAME).setValue(name);
		item.getItemProperty(TYPE_PROPERTY_VALUE).setValue(type);
	}

	@Override
	public void addListener(TransactionViewEvent eventHandeler) {
		// TODO Auto-generated method stub

	}

	public com.vaadin.ui.Component getRoot() {
		return rootlayout;
	}

}
