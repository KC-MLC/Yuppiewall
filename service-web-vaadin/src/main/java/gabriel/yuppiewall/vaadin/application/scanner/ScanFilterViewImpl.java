package gabriel.yuppiewall.vaadin.application.scanner;

import gabriel.yuppiewall.common.Tupple;
import gabriel.yuppiewall.indicator.PreconfiguredIndicator;
import gabriel.yuppiewall.indicator.TechnicalIndicator.SCAN_ON;
import gabriel.yuppiewall.indicator.repository.PreconfiguredIndicatorService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.Expression;
import gabriel.yuppiewall.scanner.domain.GlobalFilter;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.scanner.service.ScannerServive;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;
import gabriel.yuppiewall.vaadin.UIConstant;
import gabriel.yuppiewall.vaadin.YuppiewallUI;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.google.common.eventbus.EventBus;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ScanFilterViewImpl implements Serializable {

	private VerticalLayout rootlayout;
	private static final Object TYPE_PROPERTY_NAME = "name";
	private static final Object TYPE_PROPERTY_VALUE = "value";

	private static final Object INDICATOR = "Indicators";
	private static final Object PAPAMETER = "Parameters";
	private static final Object DATA_OFFSET = "Date Offset";
	private static final Object PERIOD = "Period";
	private static final Object COMPARATOR = "Comparator";

	private static final Object INDICATOR2 = "Indicators.";
	private static final Object PAPAMETER2 = "Parameters.";
	private static final Object DATA_OFFSET2 = "Date Offset.";
	private static final Object PERIOD2 = "Period.";

	@Autowired
	private EventBus eventBus;
	private Table additionalFilter;

	public ScanFilterViewImpl() {
	}

	public void onLoad() {

	}

	public void init() {

		rootlayout = new VerticalLayout();
		rootlayout.setSpacing(true);
		{
			Panel globalFilter = new Panel("Global Filter");
			// globalFilter.setWidth("100%");
			rootlayout.addComponent(globalFilter);

			VerticalLayout globalFilterLyt = (VerticalLayout) globalFilter
					.getContent();
			globalFilterLyt.setMargin(true);
			globalFilterLyt.setSpacing(true);

			ComboBox groupSelect = new ComboBox();
			// setIndexexContainer(groupSelect);
			groupSelect.setWidth(UIConstant.LONG_FIELD_WIDTH);
			/**/

			globalFilterLyt.addComponent(getFormField("Group", groupSelect));
			setIndexexContainer(groupSelect);
			setDefaultValue(groupSelect.getContainerDataSource(),
					new Tupple<String, Object>("-- Countries --", null),
					new Tupple<String, Object>("United States", "c-usa"),
					new Tupple<String, Object>("-- Exchanges --", null),
					new Tupple<String, Object>("NASDAQ", "ex-nasdaq"),
					new Tupple<String, Object>("NYSE", "ex-nyse"));
			groupSelect.select(1);
			ComboBox selectAvgVolume = new ComboBox();
			setIndexexContainer(selectAvgVolume);
			setDefaultValue(selectAvgVolume.getContainerDataSource(),
					new Tupple<String, Object>("Any", null),
					new Tupple<String, Object>(">40000", 40000),
					new Tupple<String, Object>(">60000", 60000),
					new Tupple<String, Object>(">80000", 80000),
					new Tupple<String, Object>(">100000", 100000),
					new Tupple<String, Object>(">200000", 200000),
					new Tupple<String, Object>(">300000", 300000),
					new Tupple<String, Object>(">500000", 500000),
					new Tupple<String, Object>(">1000000", 1000000));

			selectAvgVolume.select(1);
			selectAvgVolume.setWidth(UIConstant.MEDIUM_FIELD_WIDTH);
			TextField tfAvgVolume = new TextField();
			tfAvgVolume.setWidth(UIConstant.SMALL_FIELD_WIDTH);
			globalFilterLyt.addComponent(getFormField("Avg Vol",
					selectAvgVolume, "over", tfAvgVolume, "days"));
			tfAvgVolume.setValue("20");

			ComboBox selectAvgPrice = new ComboBox();
			setIndexexContainer(selectAvgPrice);
			setDefaultValue(selectAvgPrice.getContainerDataSource(),
					new Tupple<String, Object>("Any", null),
					new Tupple<String, Object>("$1.00", 1),
					new Tupple<String, Object>("$2.00", 2),
					new Tupple<String, Object>("$5.00", 5),
					new Tupple<String, Object>("$10.00", 10),
					new Tupple<String, Object>("$20.00", 20),
					new Tupple<String, Object>("$50.00", 50),
					new Tupple<String, Object>("$75.00", 75),
					new Tupple<String, Object>("$100.00", 100));
			selectAvgPrice.select(0);
			selectAvgPrice.setWidth(UIConstant.MEDIUM_FIELD_WIDTH);
			TextField tfAvgPrice = new TextField();
			tfAvgPrice.setWidth(UIConstant.SMALL_FIELD_WIDTH);
			globalFilterLyt.addComponent(getFormField("Avg Price",
					selectAvgPrice, "over", tfAvgPrice, "days"));
		}

		{
			Panel additonalFilterPanel = new Panel(
					"Additional Technical Expressions");
			// additonalFilterPanel.setWidth("100%");

			rootlayout.addComponent(additonalFilterPanel);
			VerticalLayout additonalFilterLyt = (VerticalLayout) additonalFilterPanel
					.getContent();
			additonalFilterLyt.setSpacing(true);
			additonalFilterLyt.setMargin(true);
			final ComboBox selectPreCondition = new ComboBox();
			{

				setIndexexContainer(selectPreCondition);

				{
					PreconfiguredIndicatorService preconfiguredIndicatorService = YuppiewallUI
							.getInstance().getService(
									"preconfiguredIndicatorService");
					List<PreconfiguredIndicator> list = preconfiguredIndicatorService
							.getPreconfiguredIndicator();

					Tupple<String, Object> values[] = new Tupple[list.size()];
					for (int i = 0; i < list.size(); i++) {
						PreconfiguredIndicator preconfiguredIndicator = list
								.get(i);
						values[i] = new Tupple<String, Object>(
								preconfiguredIndicator.getName(),
								preconfiguredIndicator);
					}
					setDefaultValue(
							selectPreCondition.getContainerDataSource(), values);
				}
			}

			Button btAddPreCondition = new Button("Insert");
			btAddPreCondition.addListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					Object id = selectPreCondition.getValue();
					
					PreconfiguredIndicator preconfiguredIndicator = (PreconfiguredIndicator) selectPreCondition
							.getItem(id).getItemProperty(TYPE_PROPERTY_VALUE);
					//figure out if there is a space for this configuration
					preconfiguredIndicator.g

				}
			});
			HorizontalLayout menuLayout = new HorizontalLayout();
			menuLayout.setWidth("100%");
			menuLayout.setStyleName("toolbar");
			additonalFilterLyt.addComponent(menuLayout);
			Component preConLyt = getFormField("Add predefined condition",
					selectPreCondition, btAddPreCondition);
			preConLyt.setStyleName("segment");
			menuLayout.addComponent(preConLyt);

			menuLayout.setComponentAlignment(preConLyt, Alignment.MIDDLE_LEFT);

			/*
			 * additonalFilterLyt.addComponent(getFormField(
			 * "Add predefined condition", selectPreCondition,
			 * btAddPreCondition));
			 */

			additionalFilter = new Table();
			additionalFilter.setWidth("100%");
			// turn on column reordering and collapsing
			additionalFilter.setColumnReorderingAllowed(false);
			additionalFilter.setColumnCollapsingAllowed(false);
			additionalFilter.setSortDisabled(false);
			additonalFilterLyt.addComponent(additionalFilter);
			// additonalFilterLyt.setExpandRatio(additionalFilter, 1);
			additionalFilter.setSelectable(true);
			additionalFilter.setMultiSelect(false);
			additionalFilter.setEditable(true);
			additionalFilter.setImmediate(true);
			additionalFilter.setPageLength(0);

			additionalFilter.addContainerProperty(INDICATOR, ComboBox.class,
					null);
			additionalFilter.setColumnExpandRatio(INDICATOR, 3);
			additionalFilter.addContainerProperty(PAPAMETER, TextField.class,
					"");
			additionalFilter.setColumnExpandRatio(PAPAMETER, 2);
			additionalFilter.addContainerProperty(DATA_OFFSET, TextField.class,
					"");
			additionalFilter.setColumnExpandRatio(DATA_OFFSET, 1);
			additionalFilter.addContainerProperty(PERIOD, ComboBox.class, null);
			additionalFilter.setColumnExpandRatio(PERIOD, 1);
			additionalFilter.addContainerProperty(COMPARATOR, ComboBox.class,
					null);
			additionalFilter.setColumnExpandRatio(COMPARATOR, 1);

			additionalFilter.addContainerProperty(INDICATOR2, ComboBox.class,
					null);
			additionalFilter.setColumnExpandRatio(INDICATOR2, 3);
			additionalFilter.addContainerProperty(PAPAMETER2, TextField.class,
					"");
			additionalFilter.setColumnExpandRatio(PAPAMETER2, 2);
			additionalFilter.addContainerProperty(DATA_OFFSET2,
					TextField.class, "");
			additionalFilter.setColumnExpandRatio(DATA_OFFSET2, 1);
			additionalFilter
					.addContainerProperty(PERIOD2, ComboBox.class, null);
			additionalFilter.setColumnExpandRatio(PERIOD2, 1);

			//

			{
				HorizontalLayout bottom = new HorizontalLayout();
				// bottom.setMargin(true, false, false, true);
				bottom.setSpacing(true);
				bottom.setStyleName("small-segment");
				menuLayout.addComponent(bottom);

				menuLayout
						.setComponentAlignment(bottom, Alignment.MIDDLE_RIGHT);

				// rootlayout.addComponent(bottom);

				Button btRun = new Button("Run");
				btRun.addListener(new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						ScannerServive scanner = YuppiewallUI.getInstance()
								.getService("scannerService");
						GlobalFilter gf = new GlobalFilter();
						ScanParameter param = new ScanParameter(gf);
						Tupple<String, String> group = new Tupple<String, String>(
								"country");
						group.setValue("usa");

						gf.setGroup(group);

						List<EndOfDayData> scanResult = scanner.runScan(param,
								(PrimaryPrincipal) YuppiewallUI.getInstance()
										.getApplicationData("user"));
						eventBus.post(new NewScanResult(scanResult));
					}
				});
				Button btSave = new Button("Save");
				Button btCreateAlert = new Button("Create Alert");
				bottom.addComponent(btRun);
				bottom.addComponent(btSave);
				bottom.addComponent(btCreateAlert);
				// bottom.setStyleName(")
				createEmptyRow();
				createEmptyRow();
			}

		}
	}

	int row = 0;

	void createEmptyRow() {
		Item item = additionalFilter.addItem(row++);
		item.getItemProperty(INDICATOR).setValue(getNewIndicatorCB());
		item.getItemProperty(PAPAMETER).setValue(getNewTextField());
		item.getItemProperty(DATA_OFFSET).setValue(getNewTextField());
		item.getItemProperty(PERIOD).setValue(getNewPeriodCB());

		item.getItemProperty(COMPARATOR).setValue(getNewComparatorCB());

		item.getItemProperty(INDICATOR2).setValue(getNewIndicatorCB());
		item.getItemProperty(PAPAMETER2).setValue(getNewTextField());
		item.getItemProperty(DATA_OFFSET2).setValue(getNewTextField());
		item.getItemProperty(PERIOD2).setValue(getNewPeriodCB());
	}

	private TextField getNewTextField() {
		TextField tf = new TextField();

		tf.setWidth("100%");

		return tf;
	}

	private ComboBox getNewComparatorCB() {
		ComboBox cb = new ComboBox();
		cb.setWidth("100%");
		setIndexexContainer(cb);
		setDefaultValue(cb.getContainerDataSource(),
				new Tupple<String, Object>("=", "EQ"),
				new Tupple<String, Object>(">", "GT"),
				new Tupple<String, Object>("<", "LT"));
		cb.select(0);
		return cb;
	}

	private ComboBox getNewPeriodCB() {
		ComboBox cb = new ComboBox();
		cb.setWidth("100%");
		setIndexexContainer(cb);
		setDefaultValue(cb.getContainerDataSource(),
				new Tupple<String, Object>("Days", "d"),
				new Tupple<String, Object>("Weeks", "w"));
		cb.select(0);
		return cb;
	}

	private ComboBox getNewIndicatorCB() {
		ComboBox cb = new ComboBox();
		cb.setWidth("100%");
		setIndexexContainer(cb);
		cb.setNullSelectionItemId(" ");
		setDefaultValue(cb.getContainerDataSource(),
				new Tupple<String, Object>("Constant = ", new Expression()),
				new Tupple<String, Object>("----------", null),
				new Tupple<String, Object>("SMA Close", new Expression("SMA",
						SCAN_ON.CLOSING)), new Tupple<String, Object>(
						"EMA Close", new Expression("EMA", SCAN_ON.CLOSING)),
				new Tupple<String, Object>("Max. Close", new Expression(
						"Close", SCAN_ON.CLOSING)), new Tupple<String, Object>(
						"SMA Vol.", new Expression("SMA", SCAN_ON.VOLUME)),
				new Tupple<String, Object>("EMA Vol.", new Expression("EMA",
						SCAN_ON.VOLUME)), new Tupple<String, Object>(
						"Max. Vol", new Expression("Volume", SCAN_ON.VOLUME)),
				new Tupple<String, Object>("Max. High", new Expression("High",
						SCAN_ON.HIGH)), new Tupple<String, Object>(
						"----------", null), new Tupple<String, Object>("Open",
						new Expression("Open", SCAN_ON.OPEN)),
				new Tupple<String, Object>("High", new Expression("High",
						SCAN_ON.HIGH)), new Tupple<String, Object>(
						"Constant = ", new Expression()),
				new Tupple<String, Object>("Low", new Expression("Low",
						SCAN_ON.LOW)), new Tupple<String, Object>("Close",
						new Expression("Close", SCAN_ON.CLOSING)),
				new Tupple<String, Object>("----------", null),
				new Tupple<String, Object>("RSI", new Expression("RSI",
						SCAN_ON.CLOSING)), new Tupple<String, Object>("ADX",
						new Expression("ADX", SCAN_ON.CLOSING)));

		cb.setNullSelectionAllowed(true);

		return cb;
	}

	private void setIndexexContainer(final ComboBox type) {
		IndexedContainer typeContainer = new IndexedContainer();
		typeContainer.addContainerProperty(TYPE_PROPERTY_NAME, String.class,
				null);
		typeContainer.addContainerProperty(TYPE_PROPERTY_VALUE, Object.class,
				null);
		type.setContainerDataSource(typeContainer);

		// Sets the combobox to show a certain property as the item caption
		type.setItemCaptionPropertyId(TYPE_PROPERTY_NAME);
		type.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);

		// Set the appropriate filtering mode for this example
		type.setFilteringMode(Filtering.FILTERINGMODE_STARTSWITH);
		type.setImmediate(true);

		// Disallow null selections
		type.setNullSelectionAllowed(false);
	}

	@SafeVarargs
	private final void setDefaultValue(Container container,
			Tupple<String, Object>... value) {

		for (int i = 0; i < value.length; i++) {
			Tupple tupple = value[i];
			Item item = container.addItem(i);
			item.getItemProperty(TYPE_PROPERTY_NAME).setValue(tupple.getKey());
			item.getItemProperty(TYPE_PROPERTY_VALUE).setValue(
					tupple.getValue());
		}

	}

	private Component getFormField(Object... field) {

		HorizontalLayout lyt = new HorizontalLayout();
		lyt.setSpacing(true);
		for (Object object : field) {
			if (object instanceof String) {
				lyt.addComponent(new Label((String) object));
			} else if (object instanceof Component) {
				lyt.addComponent((Component) object);
			}
		}
		return lyt;
	}

	public com.vaadin.ui.Component getRoot() {
		return rootlayout;
	}

}
