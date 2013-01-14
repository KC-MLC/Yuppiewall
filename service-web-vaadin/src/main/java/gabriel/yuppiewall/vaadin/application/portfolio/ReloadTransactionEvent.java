package gabriel.yuppiewall.vaadin.application.portfolio;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class ReloadTransactionEvent extends ApplicationEvent {

	public ReloadTransactionEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public ReloadTransactionEvent() {
		super(null);
	}

}
