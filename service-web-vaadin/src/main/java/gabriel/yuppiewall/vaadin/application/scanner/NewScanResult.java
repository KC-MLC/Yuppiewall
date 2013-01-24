package gabriel.yuppiewall.vaadin.application.scanner;

import gabriel.yuppiewall.scanner.domain.ScanOutput;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class NewScanResult extends ApplicationEvent {

	public NewScanResult(ScanOutput[] source) {
		super(source);
	}
}
