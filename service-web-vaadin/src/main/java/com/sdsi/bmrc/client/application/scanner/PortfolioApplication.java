package com.sdsi.bmrc.client.application.scanner;

import com.sdsi.bmrc.client.application.Application;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
public class PortfolioApplication implements Application<ComponentContainer> {

	public PortfolioApplication() {

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

		VerticalLayout navTopPanel = new VerticalLayout();

		navBar.setFirstComponent(navTopPanel);
		navBar.setSplitPosition(40, Sizeable.UNITS_PERCENTAGE);
		HorizontalLayout menubar = new HorizontalLayout();
		menubar.setStyleName("small-segment");
		navTopPanel.addComponent(menubar);
		{
			Button addNewPortfolio = new Button("add");
			addNewPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
			addNewPortfolio.setDescription("add new Portfolio");
			addNewPortfolio.setIcon(new ThemeResource(
					"../runo/icons/16/document-add.png"));

			menubar.addComponent(addNewPortfolio);
		}
		{
			Button addNewPortfolio = new Button("edit");
			addNewPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
			addNewPortfolio.setDescription("Edit Portfolio");
			addNewPortfolio.setIcon(new ThemeResource(
					"../runo/icons/16/document.png"));

			menubar.addComponent(addNewPortfolio);
			addNewPortfolio.setEnabled(false);
		}
		{
			Button addNewPortfolio = new Button("delete");
			addNewPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
			addNewPortfolio.setDescription("Delete Portfolio");
			addNewPortfolio.setIcon(new ThemeResource(
					"../runo/icons/16/document-delete.png"));
			addNewPortfolio.setEnabled(false);
			menubar.addComponent(addNewPortfolio);
		}

		VerticalSplitPanel contentPane = new VerticalSplitPanel();
		contentPane.setSplitPosition(30, Sizeable.UNITS_PERCENTAGE);
		hsp.setSecondComponent(contentPane);

		VerticalLayout contentPaneSearchSection = new VerticalLayout();

		contentPaneSearchSection.setMargin(true);
		contentPaneSearchSection.setSpacing(true);
		contentPane.setFirstComponent(contentPaneSearchSection);

		Tree t;
		t = new Tree("Portfolio");

		// |  | Edit portfolio | Delete
		// portfolio | Download to spreadsheet | Download to OFX

		HorizontalLayout butBar = new HorizontalLayout();
		butBar.setStyleName("small-segment");
		contentPaneSearchSection.addComponent(butBar);
		{
			Button addNewPortfolio = new Button("Import transactions");
			addNewPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
			addNewPortfolio.setDescription("Import transactions");
			
			butBar.addComponent(addNewPortfolio);
		}
		{
			Button addNewPortfolio = new Button("Edit transactions");
			addNewPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
			addNewPortfolio.setDescription("Edit transactions");
			
			butBar.addComponent(addNewPortfolio);
		}
		{
			Button addNewPortfolio = new Button("Download to spreadsheet");
			addNewPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
			addNewPortfolio.setDescription("Download to spreadsheet");
			
			butBar.addComponent(addNewPortfolio);
		}
		{
			Button addNewPortfolio = new Button("Download to OFX");
			addNewPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
			addNewPortfolio.setDescription("Download to OFX");
			
			butBar.addComponent(addNewPortfolio);
		}

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
		return "My Portfolio";
	}

	@Override
	public String getID() {
		throw new UnsupportedOperationException("getID");
	}

	@Override
	public String getThumbnail() {
		return "../wall-midnight/icons/portfolio.png";
	}
}
