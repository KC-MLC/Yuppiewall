package gabriel.yuppiewall.vaadin.application.scanner;

import gabriel.yuppiewall.common.Tupple;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
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
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class ScanFilterViewImpl implements Serializable {

	private VerticalLayout rootlayout;
	private static final Object TYPE_PROPERTY_NAME = "name";
	private static final Object TYPE_PROPERTY_VALUE = "value";

	private static final String H_COL1_WIDTH = "12em";
	private static final String H_COL2_WIDTH = "10em";
	private static final String H_COL3_WIDTH = "14em";
	private static final String H_COL4_WIDTH = "7em";

	private static final String COL1_WIDTH = "10em";
	private static final String COL2_WIDTH = "8em";
	private static final String COL3_WIDTH = "12em";
	private static final String COL4_WIDTH = "5em";

	@Autowired
	private EventBus eventBus;

	public ScanFilterViewImpl() {
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
			globalFilterLyt.setMargin(true, false, false, true);
			globalFilterLyt.setSpacing(true);

			ComboBox groupSelect = new ComboBox();
			// setIndexexContainer(groupSelect);
			groupSelect.setWidth(UIConstant.LONG_FIELD_WIDTH);
			/**/

			globalFilterLyt.addComponent(getFormField("Group", groupSelect));
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
			additonalFilterPanel.setWidth("100%");

			rootlayout.addComponent(additonalFilterPanel);
			VerticalLayout additonalFilterLyt = (VerticalLayout) additonalFilterPanel
					.getContent();
			additonalFilterLyt.setSpacing(true);
			additonalFilterLyt.setMargin(true);
			ComboBox selectPreCondition = new ComboBox();
			Button btAddPreCondition = new Button("Insert");
			additonalFilterLyt.addComponent(getFormField(
					"Add predefined condition", selectPreCondition,
					btAddPreCondition));

			/*// additonalFilterLyt.addComponent(new TableStylingExample());
			GridLayout additonalFilterGrid = new GridLayout(7, 3);
			additonalFilterGrid.setWidth("100%");
			additonalFilterLyt.addComponent(additonalFilterGrid);
			{
				Label header = new Label("Indicators etc...	");
				header.setWidth(H_COL1_WIDTH);
				additonalFilterGrid.addComponent(header, 0, 0);
				additonalFilterGrid.setComponentAlignment(header,
						Alignment.TOP_LEFT);
			}
			{
				Label header = new Label("Parameters");
				header.setWidth(H_COL2_WIDTH);
				additonalFilterGrid.addComponent(header, 1, 0);
				additonalFilterGrid.setComponentAlignment(header,
						Alignment.TOP_LEFT);
			}
			{
				Label header = new Label("Date Offset & Period	");
				header.setWidth(H_COL3_WIDTH);
				additonalFilterGrid.addComponent(header, 2, 0);
				additonalFilterGrid.setComponentAlignment(header,
						Alignment.TOP_LEFT);
			}
			{
				Label header = new Label();
				header.setWidth(H_COL4_WIDTH);
				additonalFilterGrid.addComponent(header, 3, 0);
				additonalFilterGrid.setComponentAlignment(header,
						Alignment.TOP_LEFT);
			}
			{
				Label header = new Label("Indicators etc...	");
				header.setWidth(H_COL1_WIDTH);
				additonalFilterGrid.addComponent(header, 4, 0);
				additonalFilterGrid.setComponentAlignment(header,
						Alignment.TOP_LEFT);
			}
			{
				Label header = new Label("Parameters");
				header.setWidth(H_COL2_WIDTH);
				additonalFilterGrid.addComponent(header, 5, 0);
				additonalFilterGrid.setComponentAlignment(header,
						Alignment.TOP_LEFT);
			}
			{
				Label header = new Label("Date Offset & Period	");
				header.setWidth(H_COL3_WIDTH);
				additonalFilterGrid.addComponent(header, 6, 0);
				additonalFilterGrid.setComponentAlignment(header,
						Alignment.TOP_LEFT);
			}
			//
			{
				ComboBox indicator = new ComboBox();
				indicator.setWidth(COL1_WIDTH);
				additonalFilterGrid.addComponent(indicator, 0, 1);
				additonalFilterGrid.setComponentAlignment(indicator,
						Alignment.MIDDLE_CENTER);
			}
			{
				TextField parameter = new TextField();
				parameter.setWidth(COL2_WIDTH);
				additonalFilterGrid.addComponent(parameter, 1, 1);
				additonalFilterGrid.setComponentAlignment(parameter,
						Alignment.MIDDLE_CENTER);
			}
			{
				Component period = getFormField(new TextField(),
						new ComboBox(), "ago");
				period.setWidth(COL3_WIDTH);
				additonalFilterGrid.addComponent(period, 2, 1);
				additonalFilterGrid.setComponentAlignment(period,
						Alignment.MIDDLE_CENTER);
			}
			{
				ComboBox comparater = new ComboBox();
				comparater.setWidth(COL4_WIDTH);
				additonalFilterGrid.addComponent(comparater, 3, 1);
				additonalFilterGrid.setComponentAlignment(comparater,
						Alignment.TOP_LEFT);
			}
			{
				ComboBox indicator = new ComboBox();
				indicator.setWidth(COL1_WIDTH);
				additonalFilterGrid.addComponent(indicator, 4, 1);
				additonalFilterGrid.setComponentAlignment(indicator,
						Alignment.MIDDLE_CENTER);
			}
			{
				TextField parameter = new TextField();
				parameter.setWidth(COL2_WIDTH);
				additonalFilterGrid.addComponent(parameter, 5, 1);
				additonalFilterGrid.setComponentAlignment(parameter,
						Alignment.MIDDLE_CENTER);
			}
			{
				Component period = getFormField(new TextField(),
						new ComboBox(), "ago");
				period.setWidth(COL3_WIDTH);
				additonalFilterGrid.addComponent(period, 6, 1);
				additonalFilterGrid.setComponentAlignment(period,
						Alignment.MIDDLE_CENTER);
			}

			{
				ComboBox indicator = new ComboBox();
				indicator.setWidth(COL1_WIDTH);
				additonalFilterGrid.addComponent(indicator, 0, 2);
				additonalFilterGrid.setComponentAlignment(indicator,
						Alignment.MIDDLE_CENTER);
			}
			{
				TextField parameter = new TextField();
				parameter.setWidth(COL2_WIDTH);
				additonalFilterGrid.addComponent(parameter, 1, 2);
				additonalFilterGrid.setComponentAlignment(parameter,
						Alignment.MIDDLE_CENTER);
			}
			{
				Component period = getFormField(new TextField(),
						new ComboBox(), "ago");
				period.setWidth(COL3_WIDTH);
				additonalFilterGrid.addComponent(period, 2, 2);
				additonalFilterGrid.setComponentAlignment(period,
						Alignment.MIDDLE_CENTER);
			}
			{
				ComboBox comparater = new ComboBox();
				comparater.setWidth(COL4_WIDTH);
				additonalFilterGrid.addComponent(comparater, 3, 2);
				additonalFilterGrid.setComponentAlignment(comparater,
						Alignment.TOP_LEFT);
			}
			{
				ComboBox indicator = new ComboBox();
				indicator.setWidth(COL1_WIDTH);
				additonalFilterGrid.addComponent(indicator, 4, 2);
				additonalFilterGrid.setComponentAlignment(indicator,
						Alignment.MIDDLE_CENTER);
			}
			{
				TextField parameter = new TextField();
				parameter.setWidth(COL2_WIDTH);
				additonalFilterGrid.addComponent(parameter, 5, 2);
				additonalFilterGrid.setComponentAlignment(parameter,
						Alignment.MIDDLE_CENTER);
			}
			{
				Component period = getFormField(new TextField(),
						new ComboBox(), "ago");
				period.setWidth(COL3_WIDTH);
				additonalFilterGrid.addComponent(period, 6, 2);
				additonalFilterGrid.setComponentAlignment(period,
						Alignment.MIDDLE_CENTER);
			}*/

			{
				HorizontalLayout bottom = new HorizontalLayout();
				bottom.setMargin(true, false, false, true);
				bottom.setSpacing(true);
				bottom.setStyleName("small-segment");
				rootlayout.addComponent(bottom);

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
			}

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
