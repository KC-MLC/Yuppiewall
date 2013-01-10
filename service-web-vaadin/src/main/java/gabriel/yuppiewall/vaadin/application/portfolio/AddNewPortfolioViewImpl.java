package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.common.exception.BusinessException;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.service.PortfolioService;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;
import gabriel.yuppiewall.vaadin.YuppiewallUI;

import java.io.Serializable;

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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class AddNewPortfolioViewImpl implements Serializable {

	private VerticalLayout rootLayout;
	@Autowired
	private EventBus eventBus;

	public AddNewPortfolioViewImpl() {
	}

	public void init() {
		rootLayout = new VerticalLayout();
		rootLayout.setMargin(true);
		rootLayout.setSpacing(true);
		HorizontalLayout row = new HorizontalLayout();
		rootLayout.addComponent(row);
		row.setSpacing(true);
		row.addComponent(new Label("Portfolio Name"));
		final TextField tfPortfolioName = new TextField();
		row.addComponent(tfPortfolioName);
		final Label errorMessage = new Label();
		rootLayout.addComponent(errorMessage);
		// The cancel / apply buttons
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);
		Button discardChanges = new Button("Discard changes",
				new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {
						tfPortfolioName.setValue("");
					}
				});
		discardChanges.setStyleName(BaseTheme.BUTTON_LINK);
		buttons.addComponent(discardChanges);
		buttons.setComponentAlignment(discardChanges, Alignment.MIDDLE_LEFT);

		Button apply = new Button("Apply", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				try {
					errorMessage.setValue("");
					PortfolioService ps = YuppiewallUI.getInstance()
							.getService("portfolioService");

					Portfolio p = ps.createPortfolio(new Portfolio(
							(PrimaryPrincipal) YuppiewallUI.getInstance()
									.getApplicationData("user"),
							(String) tfPortfolioName.getValue()));
					YuppiewallUI.getInstance().uiController.showNotification(
							"System Message", "Portfolio "
									+ (String) tfPortfolioName.getValue()
									+ " got created",
							Notification.TYPE_TRAY_NOTIFICATION);
					eventBus.post(new PortfolioCreatedEvent(p));
				} catch (BusinessException e) {
					errorMessage.setValue(e.getMessage());
				}
			}
		});
		buttons.addComponent(apply);
		rootLayout.addComponent(buttons);

	}

	public ComponentContainer getView() {
		return rootLayout;
	}

}
