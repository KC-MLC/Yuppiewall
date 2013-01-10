package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.instrument.domain.GenaricInstrument;
import gabriel.yuppiewall.trade.domain.Order;
import gabriel.yuppiewall.trade.domain.Order.TransactionType;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.service.AccountManager;
import gabriel.yuppiewall.vaadin.YuppiewallUI;

import java.math.BigDecimal;
import java.util.Date;

import org.vaadin.henrik.drawer.Drawer;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
public class TransactionViewImpl extends VerticalLayout implements
		TransactionView {

	private static final Object TYPE_PROPERTY_NAME = "name";
	private static final Object TYPE_PROPERTY_VALUE = "value";
	private Portfolio selectedPortfolio;

	public TransactionViewImpl() {
		setSizeFull();
		setMargin(true);
		setSpacing(true);
		// | | Edit portfolio | Delete
		// portfolio | Download to spreadsheet | Download to OFX

		HorizontalLayout butBar = new HorizontalLayout();
		butBar.setStyleName("small-segment");
		addComponent(butBar);
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

		TreeTable holdingTT = new TreeTable();
		holdingTT.setWidth("100%");
		holdingTT.setColumnCollapsingAllowed(true);
		Panel holdingTTPanel = new Panel();
		holdingTTPanel.setSizeFull();
		holdingTTPanel.addComponent(holdingTT);
		addComponent(holdingTTPanel);
		setExpandRatio(holdingTTPanel, 1);

		holdingTT.addContainerProperty("Name", String.class, "");
		holdingTT.addContainerProperty("Symbol", String.class, "");
		holdingTT.addContainerProperty("Last Price", BigDecimal.class,
				new BigDecimal(0.0));
		holdingTT.addContainerProperty("Share", Integer.class, new Integer(0));
		holdingTT.addContainerProperty("Price/Share", BigDecimal.class,
				new BigDecimal(0.0));
		holdingTT.addContainerProperty("Mkt Value", BigDecimal.class,
				new BigDecimal(0.0));
		holdingTT.addContainerProperty("Un realized P/L", BigDecimal.class,
				new BigDecimal(0.0));
		holdingTT.addContainerProperty("Un realized P/L %", BigDecimal.class,
				new BigDecimal(0.0));
		holdingTT.addContainerProperty("Realized P/L", BigDecimal.class,
				new BigDecimal(0.0));
		holdingTT.addContainerProperty("Realized P/L %", BigDecimal.class,
				new BigDecimal(0.0));

		VerticalLayout bottomLayount = new VerticalLayout();
		bottomLayount.setSpacing(true);

		Panel bottom = new Panel(bottomLayount);
		final Drawer drawer = new Drawer("Add Transaction", bottom);
		// Drawer is designed to work best with explicitly defined widths.
		drawer.setWidth("100%");
		addComponent(drawer);
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
		type.setWidth(12, UNITS_EM);

		// Set the appropriate filtering mode for this example
		type.setFilteringMode(Filtering.FILTERINGMODE_STARTSWITH);
		type.setImmediate(true);

		// Disallow null selections
		type.setNullSelectionAllowed(false);
		setTypeContainer(typeContainer);

		final PopupDateField date = new PopupDateField("Date");
		addNewTransactionLayout.addComponent(date);

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
								new GenaricInstrument(symbol));
						accountManager.placeOrder(null, selectedPortfolio,
								order);

					}
				});
		bottom.addComponent(addNewTransaction);

	}

	private void setTypeContainer(IndexedContainer container) {

		setTypeContainer(container, "Buy", TransactionType.BUY);
		setTypeContainer(container, "Sell", TransactionType.SELL);
		setTypeContainer(container, "Buy to Cover",
				TransactionType.BUY_TO_COVER);
		setTypeContainer(container, "Sell Short", TransactionType.SELL_SHORT);

	}

	private void setTypeContainer(IndexedContainer container, String name,
			TransactionType type) {

		int id = type.ordinal();
		Item item = container.addItem(id);
		item.getItemProperty(TYPE_PROPERTY_NAME).setValue(name);
		item.getItemProperty(TYPE_PROPERTY_VALUE).setValue(type);
	}

	@Override
	public void addListener(TransactionViewEvent eventHandeler) {
		// TODO Auto-generated method stub

	}
}
