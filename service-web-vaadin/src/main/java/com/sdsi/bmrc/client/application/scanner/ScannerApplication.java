package com.sdsi.bmrc.client.application.scanner;

import com.sdsi.bmrc.client.application.Application;
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
public class ScannerApplication implements Application<ComponentContainer> {

	public ScannerApplication() {

	}

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

		VerticalLayout contentPaneSearchSection = new VerticalLayout();

		contentPaneSearchSection.setMargin(true);
		contentPaneSearchSection.setSpacing(true);
		contentPane.setFirstComponent(contentPaneSearchSection);
		TextArea query = new TextArea();
		query.setSizeFull();
		query.setRows(5);
		query.setImmediate(true);
		contentPaneSearchSection.addComponent(query);
		HorizontalLayout butBar = new HorizontalLayout();
		butBar.setStyleName("small-segment");
		contentPaneSearchSection.addComponent(butBar);
		butBar.addComponent(new Button("save"));
		butBar.addComponent(new Button("run"));
		butBar.addComponent(new Button("validate"));
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
}
