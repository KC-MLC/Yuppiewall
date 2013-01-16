package gabriel.yuppiewall.vaadin.application.scanner;

import gabriel.yuppiewall.common.Tupple;
import gabriel.yuppiewall.indicator.PreconfiguredIndicator;
import gabriel.yuppiewall.indicator.TechnicalIndicator.SCAN_ON;
import gabriel.yuppiewall.indicator.repository.PreconfiguredIndicatorService;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.Expression;
import gabriel.yuppiewall.scanner.domain.GlobalFilter;
import gabriel.yuppiewall.scanner.domain.ScanParameter;
import gabriel.yuppiewall.scanner.domain.ScanParameter.OPERAND;
import gabriel.yuppiewall.scanner.domain.ScanParameter.PERIOD;
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
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.ThemeResource;
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
import com.vaadin.ui.themes.BaseTheme;

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

	private GlobalFilter gf;

	public ScanFilterViewImpl() {
	}

	public void onLoad() {
		gf = new GlobalFilter();
	}

	public void init() {

		rootlayout = new VerticalLayout();
		rootlayout.setSpacing(true);
		{
			gf = new GlobalFilter();
			Panel globalFilter = new Panel("Global Filter");
			// globalFilter.setWidth("100%");
			rootlayout.addComponent(globalFilter);

			VerticalLayout globalFilterLyt = (VerticalLayout) globalFilter
					.getContent();
			globalFilterLyt.setMargin(true);
			globalFilterLyt.setSpacing(true);

			final ComboBox groupSelect = new ComboBox();
			groupSelect.addListener(new Property.ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					Object id = groupSelect.getValue();

					String key = (String) groupSelect.getItem(id)
							.getItemProperty(TYPE_PROPERTY_VALUE).getValue();
					if (key == null) {
						gf.setGroup(null);
						// TODO add some error message
						return;
					}
					String[] k = key.split("-");

					if (k[0].equals("c")) {
						gf.setGroup(new Tupple<String, String>("country", k[1]));
					} else if (k[0].equals("ex")) {
						gf.setGroup(new Tupple<String, String>("exchange", k[1]));
					}

				}
			});
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
			groupSelect.select("United States");
			final ComboBox selectAvgVolume = new ComboBox();
			final TextField tfAvgVolume = new TextField();
			setIndexexContainer(selectAvgVolume);
			selectAvgVolume.setImmediate(true);
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

			selectAvgVolume.addListener(new Property.ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					Object id = selectAvgVolume.getValue();

					Integer key = (Integer) selectAvgVolume.getItem(id)
							.getItemProperty(TYPE_PROPERTY_VALUE).getValue();
					if (key == null) {
						gf.setAvgVolue(null);
						return;
					}
					Expression lhs = new Expression(
							"SMA",
							gabriel.yuppiewall.scanner.domain.ScanParameter.PERIOD.DAYS,
							SCAN_ON.VOLUME, 0);

					Condition avgVolue = new Condition(lhs, OPERAND.GT,
							new Expression(key.toString()));
					gf.setAvgVolue(avgVolue);

					String param = (String) tfAvgVolume.getValue();
					if (param != null && (param = param.trim()).length() != 0)
						lhs.setParameters(param);

				}
			});
			selectAvgVolume.select(">40000");
			selectAvgVolume.setWidth(UIConstant.MEDIUM_FIELD_WIDTH);

			tfAvgVolume.setImmediate(true);
			tfAvgVolume.setWidth(UIConstant.SMALL_FIELD_WIDTH);
			globalFilterLyt.addComponent(getFormField("Avg Vol",
					selectAvgVolume, "over", tfAvgVolume, "days"));

			tfAvgVolume.addListener(new Property.ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					String param = (String) tfAvgVolume.getValue();
					if (param != null && (param = param.trim()).length() != 0) {
						// validate for this to be number
						try {
							Integer.parseInt(param);
						} catch (Exception e) {
							reportError("Enter numeric value for ave volume days");
						}
						Condition cond = gf.getAvgVolue();
						if (cond == null || cond.getLhs() == null)
							return;
						cond.getLhs().setParameters(param);
					}
				}
			});
			tfAvgVolume.setValue("20");
			final ComboBox selectAvgPrice = new ComboBox();
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

			final TextField tfAvgPrice = new TextField();
			selectAvgPrice.addListener(new Property.ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					Object id = selectAvgPrice.getValue();

					Integer key = (Integer) selectAvgPrice.getItem(id)
							.getItemProperty(TYPE_PROPERTY_VALUE).getValue();
					if (key == null) {
						gf.setAvgVolue(null);
						return;
					}
					Expression lhs = new Expression(
							"SMA",
							gabriel.yuppiewall.scanner.domain.ScanParameter.PERIOD.DAYS,
							SCAN_ON.CLOSING, 0);

					Condition avgPrice = new Condition(lhs, OPERAND.GT,
							new Expression(key.toString()));
					gf.setAvgPrice(avgPrice);

					String param = (String) tfAvgPrice.getValue();
					if (param != null && (param = param.trim()).length() != 0)
						lhs.setParameters(param);

				}
			});
			selectAvgPrice.setImmediate(true);
			selectAvgPrice.select("Any");
			selectAvgPrice.setWidth(UIConstant.MEDIUM_FIELD_WIDTH);

			tfAvgPrice.setWidth(UIConstant.SMALL_FIELD_WIDTH);
			tfAvgPrice.setImmediate(true);
			tfAvgPrice.addListener(new Property.ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					String param = (String) tfAvgPrice.getValue();
					if (param != null && (param = param.trim()).length() != 0) {
						// validate for this to be number
						try {
							Integer.parseInt(param);
						} catch (Exception e) {
							reportError("Enter numeric value for Price days");
						}
						Condition cond = gf.getAvgPrice();
						if (cond == null || cond.getLhs() == null)
							return;
						cond.getLhs().setParameters(param);
					}
				}
			});
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
							.getItem(id).getItemProperty(TYPE_PROPERTY_VALUE)
							.getValue();
					// figure out if there is a space for this configuration
					int size = preconfiguredIndicator.getConditions().size();
					// TODO find target row to insert
					int emptyRow = 0;
					for (int i = 0; i < size; i++) {
						Condition con = preconfiguredIndicator.getConditions()
								.get(i);
						Expression lhs = con.getLhs();
						Item item = additionalFilter.getItem(emptyRow++);
						ComboBox cb = (ComboBox) item
								.getItemProperty(INDICATOR).getValue();
						cb.setValue(lhs.getId());
						TextField tf = (TextField) item.getItemProperty(
								PAPAMETER).getValue();
						tf.setValue(lhs.getParameters());
						tf = (TextField) item.getItemProperty(DATA_OFFSET)
								.getValue();
						tf.setValue(lhs.getOffset());

						cb = (ComboBox) item.getItemProperty(PERIOD).getValue();
						cb.setValue(0);

						Expression rhs = con.getRhs();
						cb = (ComboBox) item.getItemProperty(INDICATOR2)
								.getValue();
						cb.setValue(rhs.getId());
						tf = (TextField) item.getItemProperty(PAPAMETER2)
								.getValue();
						tf.setValue(rhs.getParameters());
						tf = (TextField) item.getItemProperty(DATA_OFFSET2)
								.getValue();
						tf.setValue(rhs.getOffset());

						cb = (ComboBox) item.getItemProperty(PERIOD2)
								.getValue();
						cb.setValue(0);

						cb = (ComboBox) item.getItemProperty(COMPARATOR)
								.getValue();
						cb.setValue(con.getOperand().getSymbol());
					}

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

				final Button btRun = new Button("Execute");
				btRun.setStyleName(BaseTheme.BUTTON_LINK);
				btRun.setDescription("Execute your Scan");
				btRun.setIcon(new ThemeResource(
						"../wall-midnight/icons/16/execute.png"));
				btRun.setImmediate(true);

				btRun.addListener(new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						ScannerServive scanner = YuppiewallUI.getInstance()
								.getService("scannerService");
						ScanParameter param = new ScanParameter(gf);
						List<EndOfDayData> scanResult = scanner.runScan(param,
								(PrimaryPrincipal) YuppiewallUI.getInstance()
										.getApplicationData("user"));
						eventBus.post(new NewScanResult(scanResult));
					}
				});
				Button btClear = new Button("Clear");
				btClear.setStyleName(BaseTheme.BUTTON_LINK);
				btClear.setDescription("Clear Scan Option");
				btClear.setIcon(new ThemeResource(
						"../wall-midnight/icons/16/clear-form.png"));
				btClear.setImmediate(true);

				Button btSave = new Button("Save");
				btSave.setStyleName(BaseTheme.BUTTON_LINK);
				btSave.setDescription("Save your Scan");
				btSave.setIcon(new ThemeResource(
						"../runo/icons/16/document-add.png"));
				btSave.setImmediate(true);

				Button btCreateAlert = new Button("Create Alert");
				btCreateAlert.setStyleName(BaseTheme.BUTTON_LINK);
				btCreateAlert.setDescription("Create Alert from your scan");
				btCreateAlert.setIcon(new ThemeResource(
						"../wall-midnight/icons/16/create-alert.png"));
				btCreateAlert.setImmediate(true);
				bottom.addComponent(btRun);
				bottom.addComponent(btClear);
				bottom.addComponent(btSave);
				bottom.addComponent(btCreateAlert);

				createEmptyRow();
				createEmptyRow();
				createEmptyRow();
				createEmptyRow();
			}

		}
	}

	protected void reportError(String string) {
		// TODO Auto-generated method stub

	}

	private int row = 0;

	private void createEmptyRow() {
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
		setDefaultValueIndicator(cb.getContainerDataSource(),
				new Tupple<String, Expression>("Constant = ", null),
				new Tupple<String, Expression>("----------", null),
				new Tupple<String, Expression>("SMA Close", new Expression(
						"SMA.C", "SMA", SCAN_ON.CLOSING)),
				new Tupple<String, Expression>("EMA Close", new Expression(
						"EMA.C", "EMA", SCAN_ON.CLOSING)),
				new Tupple<String, Expression>("Max. Close", new Expression(
						"MCLOSE", "Close", SCAN_ON.CLOSING)),
				new Tupple<String, Expression>("SMA Vol.", new Expression(
						"SMA.V", "SMA", SCAN_ON.VOLUME)),
				new Tupple<String, Expression>("EMA Vol.", new Expression(
						"EMA.V", "EMA", SCAN_ON.VOLUME)),
				new Tupple<String, Expression>("Max. Vol", new Expression(
						"MVOLUME", "Volume", SCAN_ON.VOLUME)),
				new Tupple<String, Expression>("Max. High", new Expression(
						"MHIGH", "High", SCAN_ON.HIGH)),
				new Tupple<String, Expression>("----------", null),
				new Tupple<String, Expression>("Open", new Expression("OPEN",
						"Open", SCAN_ON.OPEN)), new Tupple<String, Expression>(
						"High", new Expression("HIGH", "High", SCAN_ON.HIGH)),
				new Tupple<String, Expression>("Low", new Expression("LOW",
						"Low", SCAN_ON.LOW)), new Tupple<String, Expression>(
						"Close", new Expression("CLOSE", "Close",
								SCAN_ON.CLOSING)),
				new Tupple<String, Expression>("----------", null),
				new Tupple<String, Expression>("RSI", new Expression("RSI.C",
						"RSI", SCAN_ON.CLOSING)),
				new Tupple<String, Expression>("ADX", new Expression("ADX.C",
						"ADX", SCAN_ON.CLOSING)));

		cb.setNullSelectionAllowed(true);

		return cb;
	}

	@SafeVarargs
	private final void setDefaultValueIndicator(Container container,
			Tupple<String, Expression>... value) {
		for (int i = 0; i < value.length; i++) {
			Tupple<String, Expression> tupple = value[i];

			Expression v = tupple.getValue();
			String k = (v != null) ? v.getId() : "" + i;

			Item item = container.addItem(k);
			item.getItemProperty(TYPE_PROPERTY_NAME).setValue(tupple.getKey());
			item.getItemProperty(TYPE_PROPERTY_VALUE).setValue(
					tupple.getValue());
		}

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
			Object k = tupple.getKey();
			Object v = tupple.getValue();
			if (k == null || v == null)
				k = new Integer(i);
			Item item = container.addItem(k);
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
