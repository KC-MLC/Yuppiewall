package gabriel.yuppiewall.vaadin.application.scanner;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.google.common.eventbus.EventBus;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
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
			rootlayout.addComponent(globalFilter);
			VerticalLayout globalFilterLyt = (VerticalLayout) globalFilter
					.getContent();
			globalFilterLyt.setSpacing(true);
			globalFilterLyt.setMargin(true);

			ComboBox groupSelect = new ComboBox();
			globalFilterLyt.addComponent(getFormField("Group", groupSelect));
			ComboBox selectAvgVolume = new ComboBox();
			TextField tfAvgVolume = new TextField();
			globalFilterLyt.addComponent(getFormField("Avg Vol",
					selectAvgVolume, "over", tfAvgVolume, "days"));

			ComboBox selectAvgPrice = new ComboBox();
			TextField tfAvgPrice = new TextField();
			globalFilterLyt.addComponent(getFormField("Avg Price",
					selectAvgPrice, "over", tfAvgPrice, "days"));
		}
		{
			Panel additonalFilterPanel = new Panel(
					"Additional Technical Expressions");
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
