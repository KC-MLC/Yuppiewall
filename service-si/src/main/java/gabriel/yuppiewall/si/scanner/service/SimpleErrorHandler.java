package gabriel.yuppiewall.si.scanner.service;

import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component("errorHandler")
public class SimpleErrorHandler implements ErrorHandler {
	@Override
	public void handleError(final Throwable t) {
		t.printStackTrace();
	}
}