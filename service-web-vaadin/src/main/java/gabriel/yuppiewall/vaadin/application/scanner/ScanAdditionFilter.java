package gabriel.yuppiewall.vaadin.application.scanner;

import gabriel.yuppiewall.common.Command;
import gabriel.yuppiewall.common.Tupple;
import gabriel.yuppiewall.indicator.TechnicalIndicator.SCAN_ON;
import gabriel.yuppiewall.scanner.domain.Condition;
import gabriel.yuppiewall.scanner.domain.Expression;
import gabriel.yuppiewall.scanner.domain.ScanParameter.OPERAND;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.BaseTheme;

public class ScanAdditionFilter {

	private Table additionalFilter;
	private int rowIndex = 0;

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
	private static final Object DEL = "x";

	private List<RowApplication> rowMaping = new ArrayList<>();

	public ScanAdditionFilter() {

		additionalFilter = new Table();
		additionalFilter.setWidth("100%");
		// turn on column reordering and collapsing
		additionalFilter.setColumnReorderingAllowed(false);
		additionalFilter.setColumnCollapsingAllowed(false);
		additionalFilter.setSortDisabled(false);
		additionalFilter.setSelectable(true);
		additionalFilter.setMultiSelect(true);
		additionalFilter.setEditable(true);
		additionalFilter.setImmediate(true);
		additionalFilter.setPageLength(0);

		additionalFilter.addContainerProperty(INDICATOR, ComboBox.class, null);
		additionalFilter.setColumnExpandRatio(INDICATOR, 3);
		additionalFilter.addContainerProperty(PAPAMETER, TextField.class, "");
		additionalFilter.setColumnExpandRatio(PAPAMETER, 2);
		additionalFilter.addContainerProperty(DATA_OFFSET, TextField.class, "");
		additionalFilter.setColumnExpandRatio(DATA_OFFSET, 1);
		additionalFilter.addContainerProperty(PERIOD, ComboBox.class, null);
		additionalFilter.setColumnExpandRatio(PERIOD, 1);
		additionalFilter.addContainerProperty(COMPARATOR, ComboBox.class, null);
		additionalFilter.setColumnExpandRatio(COMPARATOR, 1);

		additionalFilter.addContainerProperty(INDICATOR2, ComboBox.class, null);
		additionalFilter.setColumnExpandRatio(INDICATOR2, 3);
		additionalFilter.addContainerProperty(PAPAMETER2, TextField.class, "");
		additionalFilter.setColumnExpandRatio(PAPAMETER2, 2);
		additionalFilter
				.addContainerProperty(DATA_OFFSET2, TextField.class, "");
		additionalFilter.setColumnExpandRatio(DATA_OFFSET2, 1);
		additionalFilter.addContainerProperty(PERIOD2, ComboBox.class, null);
		additionalFilter.setColumnExpandRatio(PERIOD2, 1);

		additionalFilter.addContainerProperty(DEL, Button.class, null);

	}

	public void insertRow(List<Condition> conditions) {

		// figure out if there is a space for this configuration
		int size = conditions.size();
		// TODO find target row to insert

		int emptyRow = getFirstEmptyIndex(size);
		if (emptyRow == -1) {
			emptyRow = createEmptyRow(size);
		}
		for (int i = 0; i < size; i++) {
			Condition con = conditions.get(i);
			Expression lhs = con.getLhs();
			// Item item = additionalFilter.getItem(emptyRow++);
			RowApplication row = rowMaping.get(emptyRow++);
			ComboBox cb = row.getIndicatorLHS();
			cb.setValue(lhs.getId());
			TextField tf = row.getParameterLHS();
			tf.setValue(lhs.getParameters());
			tf = row.getDateOffSetLHS();
			tf.setValue(lhs.getOffset());

			cb = row.getPeriodLHS();
			cb.setValue("Days");

			Expression rhs = con.getRhs();
			cb = row.getIndicatorRHS();

			cb.setValue(rhs.getId());
			tf = row.getParameterRHS();
			tf.setValue(rhs.getParameters());
			tf = row.getDateOffSetRHS();
			tf.setValue(rhs.getOffset());

			cb = row.getPeriodRHS();
			cb.setValue(0);

			cb = row.getComparator();
			cb.setValue(con.getOperand().getSymbol());
		}

	}

	private int createEmptyRow(int size) {
		int i = rowMaping.size() - 1;
		int emptyRow = 0;
		for (; 0 > i; i--) {
			if (!rowMaping.get(i).isEmpty()) {
				break;
			}
			emptyRow++;
		}

		for (int j = 0; j < (size - emptyRow); j++)
			createEmptyRow();
		return i;

	}

	private int getFirstEmptyIndex(int required) {
		for (int i = 0; i < rowMaping.size(); i++) {
			RowApplication row = rowMaping.get(i);
			if (row.isEmpty()) {
				if ((i + required) > rowMaping.size() - 1)
					return -1;
				if (hasRequiredRow(i, i + required)) {
					return i;
				}
			}
		}
		return -1;
	}

	private boolean hasRequiredRow(int index, int end) {
		for (; index < end; index++) {
			RowApplication row = rowMaping.get(index);
			if (!row.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	private Command<RowApplication> removeRow = new Command<RowApplication>() {

		@Override
		public void execute(RowApplication objectID) {
			removeRow(objectID);

		}
	};

	private void removeRow(RowApplication row) {
		rowMaping.remove(row);
		additionalFilter.removeItem(row.getRowId());
	}

	public void createEmptyRow() {
		int rowID = rowIndex++;
		Item item = additionalFilter.addItem(rowID);
		RowApplication row = new RowApplication(rowID, removeRow);
		rowMaping.add(row);
		item.getItemProperty(INDICATOR).setValue(row.getIndicatorLHS());
		item.getItemProperty(PAPAMETER).setValue(row.getParameterLHS());
		item.getItemProperty(DATA_OFFSET).setValue(row.getDateOffSetLHS());
		item.getItemProperty(PERIOD).setValue(row.getPeriodLHS());

		item.getItemProperty(COMPARATOR).setValue(row.getComparator());

		item.getItemProperty(INDICATOR2).setValue(row.getIndicatorRHS());
		item.getItemProperty(PAPAMETER2).setValue(row.getParameterRHS());
		item.getItemProperty(DATA_OFFSET2).setValue(row.getDateOffSetRHS());
		item.getItemProperty(PERIOD2).setValue(row.getPeriodRHS());
		item.getItemProperty(DEL).setValue(row.getDelete());
	}

	public Table getAdditionalFilter() {
		return additionalFilter;
	}

	static class RowApplication {
		private Condition condition;

		private ComboBox indicatorLHS;
		private TextField parameterLHS;
		private ComboBox periodLHS;
		private TextField dateOffSetLHS;
		private ComboBox comparator;
		private ComboBox indicatorRHS;
		private TextField parameterRHS;
		private ComboBox periodRHS;
		private TextField dateOffSetRHS;
		private Button delete;

		private Integer rowId;

		@SuppressWarnings("serial")
		public RowApplication(Integer rowId,
				final Command<RowApplication> oneRemove) {
			this.rowId = rowId;

			indicatorLHS = getNewIndicatorCB();
			indicatorLHS.addListener(new Property.ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					Object id = indicatorLHS.getValue();
					if (id == null)
						return;

					Expression lhs = (Expression) indicatorLHS.getItem(id)
							.getItemProperty(TYPE_PROPERTY_VALUE).getValue();
					if (lhs == null) {
						condition = null;
						return;
					} else {
						if (condition == null)
							condition = new Condition();
					}
					condition.setLhs(lhs);
					setParam(lhs, parameterLHS);
					setOffset(lhs, dateOffSetLHS);

					setOperand();

					setRHS();
				}
			});

			parameterLHS = getNewTextField();
			parameterLHS.addListener(new Property.ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					if (condition == null || condition.getLhs() == null)
						return;
					setParam(condition.getLhs(), parameterLHS);
				}
			});
			periodLHS = getNewPeriodCB();
			periodLHS.select("Days");
			dateOffSetLHS = getNewTextField();
			dateOffSetLHS.addListener(new Property.ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					if (condition == null || condition.getLhs() == null)
						return;
					setOffset(condition.getLhs(), dateOffSetLHS);
				}
			});

			comparator = getNewComparatorCB();
			comparator.addListener(new Property.ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					if (condition == null)
						return;
					setOperand();
				}
			});

			indicatorRHS = getNewIndicatorCB();
			indicatorRHS.addListener(new Property.ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					if (condition == null)
						return;
					setRHS();
				}
			});
			parameterRHS = getNewTextField();
			parameterRHS.addListener(new Property.ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					if (condition == null || condition.getRhs() == null)
						return;
					setParam(condition.getRhs(), parameterRHS);
				}
			});

			periodRHS = getNewPeriodCB();
			periodRHS.select("Days");
			dateOffSetRHS = getNewTextField();
			dateOffSetRHS.addListener(new Property.ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {
					if (condition == null || condition.getRhs() == null)
						return;
					setOffset(condition.getRhs(), dateOffSetRHS);
				}
			});
			delete = new Button();
			delete.setStyleName(BaseTheme.BUTTON_LINK);
			delete.setDescription("Remove scan");
			delete.setIcon(new ThemeResource("../runo/icons/16/trash.png"));
			delete.setImmediate(true);

			delete.addListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					oneRemove.execute(RowApplication.this);
				}
			});
		}

		// class Indicator

		public boolean isEmpty() {
			return (condition == null);
		}

		protected void setRHS() {
			Object id = indicatorRHS.getValue();
			if (id == null)
				return;
			Expression rhs = (Expression) indicatorRHS.getItem(id)
					.getItemProperty(TYPE_PROPERTY_VALUE).getValue();
			if (condition == null)
				return;

			condition.setRhs(rhs);
			if (rhs != null) {
				setParam(condition.getRhs(), parameterRHS);
				setOffset(condition.getRhs(), dateOffSetRHS);
			}
		}

		protected void setOperand() {
			Object id = comparator.getValue();
			if (id == null)
				return;
			OPERAND operand = (OPERAND) comparator.getItem(id)
					.getItemProperty(TYPE_PROPERTY_VALUE).getValue();
			if (operand == null) {
				return;
			}
			if (condition != null) {
				condition.setOperand(operand);
			}
		}

		protected void setOffset(Expression exp, TextField tf) {
			if (exp == null)
				return;
			String param = (String) tf.getValue().toString();
			if (param != null && (param = param.trim()).length() == 0)
				param = null;
			exp.setOffset((param == null) ? null : Integer.parseInt(param));

		}

		protected void setParam(Expression exp, TextField tf) {
			if (exp == null)
				return;
			String param = (String) tf.getValue();

			if (param != null && (param = param.trim()).length() == 0)
				param = null;
			exp.setParameters(param);

		}

		public ComboBox getIndicatorLHS() {
			return indicatorLHS;
		}

		public TextField getParameterLHS() {
			return parameterLHS;
		}

		public ComboBox getPeriodLHS() {
			return periodLHS;
		}

		public TextField getDateOffSetLHS() {
			return dateOffSetLHS;
		}

		public ComboBox getComparator() {
			return comparator;
		}

		public Integer getRowId() {
			return rowId;
		}

		public ComboBox getIndicatorRHS() {
			return indicatorRHS;
		}

		public TextField getParameterRHS() {
			return parameterRHS;
		}

		public ComboBox getPeriodRHS() {
			return periodRHS;
		}

		public TextField getDateOffSetRHS() {
			return dateOffSetRHS;
		}

		public Button getDelete() {
			return delete;
		}

		private ComboBox getNewPeriodCB() {
			ComboBox cb = new ComboBox();
			cb.setWidth("100%");
			setIndexexContainer(cb);
			setDefaultValue(cb.getContainerDataSource(),
					new Tupple<String, Object>("Days", "d"),
					new Tupple<String, Object>("Weeks", "w"));

			return cb;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((rowId == null) ? 0 : rowId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RowApplication other = (RowApplication) obj;
			if (rowId == null) {
				if (other.rowId != null)
					return false;
			} else if (!rowId.equals(other.rowId))
				return false;
			return true;
		}

		private void setIndexexContainer(final ComboBox type) {
			IndexedContainer typeContainer = new IndexedContainer();
			typeContainer.addContainerProperty(TYPE_PROPERTY_NAME,
					String.class, null);
			typeContainer.addContainerProperty(TYPE_PROPERTY_VALUE,
					Object.class, null);
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
		private final void setDefaultValueIndicator(Container container,
				Tupple<String, Expression>... value) {
			for (int i = 0; i < value.length; i++) {
				Tupple<String, Expression> tupple = value[i];

				Expression v = tupple.getValue();
				String k = (v != null) ? v.getId() : "" + i;

				Item item = container.addItem(k);
				item.getItemProperty(TYPE_PROPERTY_NAME).setValue(
						tupple.getKey());
				item.getItemProperty(TYPE_PROPERTY_VALUE).setValue(
						tupple.getValue());
			}

		}

		private ComboBox getNewComparatorCB() {
			ComboBox cb = new ComboBox();
			cb.setWidth("100%");
			setIndexexContainer(cb);
			setDefaultValue(cb.getContainerDataSource(),
					new Tupple<String, Object>("=", OPERAND.EQUAL),
					new Tupple<String, Object>(">", OPERAND.GT),
					new Tupple<String, Object>("<", OPERAND.LT));

			return cb;
		}

		private TextField getNewTextField() {
			TextField tf = new TextField();

			tf.setWidth("100%");

			return tf;
		}

		@SafeVarargs
		private final void setDefaultValue(Container container,
				Tupple<String, Object>... value) {

			for (int i = 0; i < value.length; i++) {
				Tupple<String, Object> tupple = value[i];
				Object k = tupple.getKey();
				Object v = tupple.getValue();
				if (k == null || v == null)
					k = new Integer(i);
				Item item = container.addItem(k);
				item.getItemProperty(TYPE_PROPERTY_NAME).setValue(
						tupple.getKey());
				item.getItemProperty(TYPE_PROPERTY_VALUE).setValue(
						tupple.getValue());
			}

		}

		private ComboBox getNewIndicatorCB() {
			ComboBox cb = new ComboBox();
			cb.setWidth("100%");
			setIndexexContainer(cb);
			cb.setNullSelectionItemId(" ");
			setDefaultValueIndicator(
					cb.getContainerDataSource(),
					new Tupple<String, Expression>("Constant = ",
							new Expression("Constant", "Constant", null)),
					new Tupple<String, Expression>("----------", null),
					new Tupple<String, Expression>("SMA Close", new Expression(
							"SMA.C", "SMA", SCAN_ON.CLOSING)),
					new Tupple<String, Expression>("EMA Close", new Expression(
							"EMA.C", "EMA", SCAN_ON.CLOSING)),
					new Tupple<String, Expression>("Max. Close",
							new Expression("MCLOSE", "Close", SCAN_ON.CLOSING)),
					new Tupple<String, Expression>("SMA Vol.", new Expression(
							"SMA.V", "SMA", SCAN_ON.VOLUME)),
					new Tupple<String, Expression>("EMA Vol.", new Expression(
							"EMA.V", "EMA", SCAN_ON.VOLUME)),
					new Tupple<String, Expression>("Max. Vol", new Expression(
							"MVOLUME", "Volume", SCAN_ON.VOLUME)),
					new Tupple<String, Expression>("Max. High", new Expression(
							"MHIGH", "High", SCAN_ON.HIGH)),
					new Tupple<String, Expression>("----------", null),
					new Tupple<String, Expression>("Open", new Expression(
							"OPEN", "Open", SCAN_ON.OPEN)),
					new Tupple<String, Expression>("High", new Expression(
							"HIGH", "High", SCAN_ON.HIGH)),
					new Tupple<String, Expression>("Low", new Expression("LOW",
							"Low", SCAN_ON.LOW)),
					new Tupple<String, Expression>("Close", new Expression(
							"CLOSE", "Close", SCAN_ON.CLOSING)),
					new Tupple<String, Expression>("----------", null),
					new Tupple<String, Expression>("RSI", new Expression(
							"RSI.C", "RSI", SCAN_ON.CLOSING)),
					new Tupple<String, Expression>("ADX", new Expression(
							"ADX.C", "ADX", SCAN_ON.CLOSING)));

			cb.setNullSelectionAllowed(true);

			return cb;
		}
	}

	public List<Condition> getInsertedCondition() {
		ArrayList<Condition> retValue = new ArrayList<>();
		for (int i = 0; i < rowMaping.size(); i++) {
			RowApplication row = rowMaping.get(i);
			if (row.condition != null) {
				retValue.add(row.condition);
			}

		}
		return retValue;
	}
}