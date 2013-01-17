package gabriel.yuppiewall.vaadin.application.scanner;

import gabriel.yuppiewall.scanner.domain.ScanOutput;

import java.util.List;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class NewScanResult extends ApplicationEvent {

	public NewScanResult(List<ScanOutput> source) {
		super(source);
	}
}
