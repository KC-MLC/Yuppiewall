package gabriel.yuppiewall.gwt.client.scan;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class SimpleScannerShell extends Composite implements HasText {

	private static SimpleScannerShellUiBinder uiBinder = GWT
			.create(SimpleScannerShellUiBinder.class);

	interface SimpleScannerShellUiBinder extends
			UiBinder<Widget, SimpleScannerShell> {
	}

	public SimpleScannerShell() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		
	}

}
