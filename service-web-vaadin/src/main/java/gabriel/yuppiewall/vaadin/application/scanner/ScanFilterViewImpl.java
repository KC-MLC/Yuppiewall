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
			groupSelect.setWidth(UIConstant.LONG_FIELD_WIDTH);
			globalFilterLyt.addComponent(getFormField("Group", groupSelect));
			ComboBox selectAvgVolume = new ComboBox();
			selectAvgVolume.setWidth(UIConstant.MEDIUM_FIELD_WIDTH);
			TextField tfAvgVolume = new TextField();
			tfAvgVolume.setWidth(UIConstant.SMALL_FIELD_WIDTH);
			globalFilterLyt.addComponent(getFormField("Avg Vol",
					selectAvgVolume, "over", tfAvgVolume, "days"));

			ComboBox selectAvgPrice = new ComboBox();
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

			// rootlayout.addComponent(additonalFilterPanel);
			VerticalLayout additonalFilterLyt = (VerticalLayout) additonalFilterPanel
					.getContent();
			additonalFilterLyt.setSpacing(true);
			additonalFilterLyt.setMargin(true);
			ComboBox selectPreCondition = new ComboBox();
			Button btAddPreCondition = new Button("Insert");
			additonalFilterLyt.addComponent(getFormField(
					"Add predefined condition", selectPreCondition,
					btAddPreCondition));

			// additonalFilterLyt.addComponent(new TableStylingExample());
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
			}

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
								.getService("scannerServive");
						ScanParameter param = new ScanParameter();
						GlobalFilter gf = new GlobalFilter();
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
