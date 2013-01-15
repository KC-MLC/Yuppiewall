package gabriel.yuppiewall.vaadin.application.scanner;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class NewScanResult extends ApplicationEvent {

	public NewScanResult(Object source) {
		super(source);
	}
}
