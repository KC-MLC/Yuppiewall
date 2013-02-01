package gabriel.yuppiewall.vaadin.application.scanner;

import java.util.List;

import gabriel.yuppiewall.scanner.domain.ScanOutput;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class NewScanResult extends ApplicationEvent {

	public NewScanResult(List<ScanOutput> source) {
		super(source);
	}
}
