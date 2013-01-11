package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.common.exception.BusinessException;
import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.service.PortfolioService;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;
import gabriel.yuppiewall.vaadin.YuppiewallUI;
import gabriel.yuppiewall.vaadin.application.SubComponentView;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.google.common.eventbus.EventBus;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class EditPortfolioViewImpl implements Serializable, SubComponentView {

	private VerticalLayout rootLayout;
	@Autowired
	private EventBus eventBus;
	private TextField tfPortfolioName;
	private Portfolio oldPortfolio;
	private TwinColSelect all_selected;

	private List<Instrument> allHolding;
	private List<Instrument> selectedHolding;

	public EditPortfolioViewImpl() {
	}

	@Override
	public void init() {
		rootLayout = new VerticalLayout();
		rootLayout.setMargin(true);
		rootLayout.setSpacing(true);
		HorizontalLayout row = new HorizontalLayout();
		rootLayout.addComponent(row);
		row.setSpacing(true);
		row.addComponent(new Label("Portfolio Name"));
		tfPortfolioName = new TextField();
		row.addComponent(tfPortfolioName);
		all_selected = new TwinColSelect();
		rootLayout.addComponent(all_selected);
		all_selected.setRows(10);
		all_selected.setNullSelectionAllowed(true);
		all_selected.setMultiSelect(true);
		all_selected.setImmediate(true);
		// all_selected.addListener(this);
		all_selected.setLeftColumnCaption("All Holdings");
		all_selected.setRightColumnCaption("Selected Holdings");
		all_selected.setWidth("350px");

		final Label errorMessage = new Label();
		rootLayout.addComponent(errorMessage);
		// The cancel / apply buttons
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);
		Button discardChanges = new Button("Discard changes",
				new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {
						loadData();

					}
				});
		discardChanges.setStyleName(BaseTheme.BUTTON_LINK);
		buttons.addComponent(discardChanges);
		buttons.setComponentAlignment(discardChanges, Alignment.MIDDLE_LEFT);

		Button apply = new Button("Apply", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				try {
					errorMessage.setValue("");
					// check if anything has changed
					// check for name change

					PortfolioService ps = YuppiewallUI.getInstance()
							.getService("portfolioService");

					Portfolio p = ps.createPortfolio(new Portfolio(
							(PrimaryPrincipal) YuppiewallUI.getInstance()
									.getApplicationData("user"),
							(String) tfPortfolioName.getValue()));
					YuppiewallUI.getInstance().uiController.showNotification(
							"System Message", "Portfolio "
									+ (String) tfPortfolioName.getValue()
									+ " got modified",
							Notification.TYPE_TRAY_NOTIFICATION);
					eventBus.post(new PortfolioCreatedEvent(p));
				} catch (BusinessException e) {
					errorMessage.setValue(e.getMessage());
				}
			}
		});
		apply.setEnabled(false);
		buttons.addComponent(apply);
		rootLayout.addComponent(buttons);

	}

	public void loadData(Portfolio oldPortfolio, List<Instrument> allHolding,
			List<Instrument> selectedHolding) {
		this.oldPortfolio = oldPortfolio;
		this.allHolding = allHolding;
		this.selectedHolding = selectedHolding;
		loadData();
	}

	private void loadData() {
		tfPortfolioName.setValue(oldPortfolio.getPortfolioName());
		all_selected.removeAllItems();

		for (Instrument instrument : allHolding) {
			all_selected.addItem(instrument);
		}
		for (Instrument instrument : selectedHolding) {
			all_selected.select(instrument);
		}

	}

	@Override
	public ComponentContainer getView() {
		return rootLayout;
	}

}
