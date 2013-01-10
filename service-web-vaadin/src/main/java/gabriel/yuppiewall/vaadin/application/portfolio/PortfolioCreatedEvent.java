package gabriel.yuppiewall.vaadin.application.portfolio;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class PortfolioCreatedEvent extends ApplicationEvent {

	public PortfolioCreatedEvent(Object source) {
		super(source);
	}

}
