package gabriel.yuppiewall.vaadin.application.scanner;

import gabriel.yuppiewall.vaadin.application.Application;
import gabriel.yuppiewall.vaadin.application.portfolio.TransactionViewImpl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class ScannerApplication implements Application<ComponentContainer>,
		Serializable {

	public ScannerApplication() {

	}

	@Autowired
	private ScanFilterViewImpl scanFilterView;

	public boolean initialize;
	private VerticalLayout applicationUI;

	@Override
	public void initialize() {
		// setMargin(true);
		// setSpacing(true);
		// make it fill the whole window
		if (initialize)
			return;
		applicationUI = new VerticalLayout();
		applicationUI.setSizeFull();
		HorizontalSplitPanel hsp = new HorizontalSplitPanel();
		hsp.setSplitPosition(30, Sizeable.UNITS_PERCENTAGE);

		applicationUI.addComponent(hsp);
		VerticalSplitPanel navBar = new VerticalSplitPanel();
		hsp.setFirstComponent(navBar);

		VerticalSplitPanel contentPane = new VerticalSplitPanel();
		contentPane.setSplitPosition(30, Sizeable.UNITS_PERCENTAGE);
		hsp.setSecondComponent(contentPane);

		scanFilterView.init();
		contentPane.setFirstComponent(scanFilterView.getRoot());

		TabSheet tabSheet = new TabSheet();
		tabSheet.setSizeFull();
		contentPane.setSecondComponent(tabSheet);

		initialize = true;
	}

	@Override
	public String getName() {
		throw new UnsupportedOperationException("getName");
	}

	@Override
	public boolean isInitialize() {
		return initialize;
	}

	@Override
	public ComponentContainer getApplicationUI() {
		return applicationUI;
	}

	@Override
	public String getTitle() {
		return "Scanner";
	}

	@Override
	public String getID() {
		throw new UnsupportedOperationException("getID");
	}

	@Override
	public String getThumbnail() {
		return "../wall-midnight/icons/scanericon.png";
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub

	}
}
