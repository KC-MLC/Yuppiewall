package gabriel.yuppiewall.vaadin.application.portfolio;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class PortfolioSelectedEvent extends ApplicationEvent{

	public PortfolioSelectedEvent(Object source) {
		super(source);		
	}

}
