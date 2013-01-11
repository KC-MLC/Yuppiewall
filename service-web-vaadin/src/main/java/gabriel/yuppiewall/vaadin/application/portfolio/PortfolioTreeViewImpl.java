package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.service.AccountManager;
import gabriel.yuppiewall.trade.service.PortfolioService;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;
import gabriel.yuppiewall.vaadin.YuppiewallUI;
import gabriel.yuppiewall.vaadin.application.SubComponentView;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class PortfolioTreeViewImpl implements PortfolioTreeView, Serializable {
	private VerticalLayout rootlayout;
	private HierarchicalContainer portfolioContainer;

	private int portfolioCount;
	private Item allHolding;
	private Window addNewPortfolio;
	private Window editPortfolio;

	private Portfolio selectedPortfolio;

	@Autowired
	private EventBus eventBus;

	class PortfolioCreatedRecorder {
		@Subscribe
		public void recordPortfolioCreated(PortfolioCreatedEvent e) {
			// close the window
			YuppiewallUI.getInstance().uiController.getWindow().removeWindow(
					addNewPortfolio);
			updateTree();
		}
	}

	@Autowired
	private AddNewPortfolioViewImpl addNewPortfolioViewImpl;
	@Autowired
	private EditPortfolioViewImpl editPortfolioViewImpl;

	private PortfolioTreeViewEvent eventHandeler = new PortfolioTreeViewPresenter();
	private Portfolio rootNode;
	private Tree portfolioTree;

	public PortfolioTreeViewImpl() {
	}

	public void init() {
		eventBus.register(new PortfolioCreatedRecorder());
		rootlayout = new VerticalLayout();
		HorizontalLayout menubar = new HorizontalLayout();
		menubar.setStyleName("small-segment");
		rootlayout.addComponent(menubar);

		final Button addNewPortfolioButton = new Button("add");
		addNewPortfolioButton.setStyleName(BaseTheme.BUTTON_LINK);
		addNewPortfolioButton.setDescription("add new Portfolio");
		addNewPortfolioButton.setIcon(new ThemeResource(
				"../runo/icons/16/document-add.png"));

		addNewPortfolioButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// openAddNewPortfolioWindow();
				addNewPortfolio = openWindow(addNewPortfolio,
						"Create New Portfolio", addNewPortfolioViewImpl);
			}
		});

		menubar.addComponent(addNewPortfolioButton);
		final Button editPortfolioWindow = new Button("edit");
		editPortfolioWindow.setStyleName(BaseTheme.BUTTON_LINK);
		editPortfolioWindow.setDescription("Edit Portfolio");
		editPortfolioWindow.setIcon(new ThemeResource(
				"../runo/icons/16/document.png"));
		editPortfolioWindow.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				editPortfolio = openWindow(editPortfolio, "Edit Portfolio",
						editPortfolioViewImpl);

				PortfolioService ps = YuppiewallUI.getInstance().getService(
						"portfolioService");
				AccountManager accountManager = YuppiewallUI.getInstance()
						.getService("accountManager");
				List<Instrument> selectedHolding = ps
						.getPortfolioInstrument(selectedPortfolio);
				List<Instrument> allHoldingList = accountManager
						.getAllInstrument((PrimaryPrincipal) YuppiewallUI
								.getInstance().getApplicationData("user"));
				editPortfolioViewImpl.loadData(selectedPortfolio,
						allHoldingList, selectedHolding);

			}
		});
		menubar.addComponent(editPortfolioWindow);
		editPortfolioWindow.setEnabled(false);
		final Button deletePortfolio = new Button("delete");
		deletePortfolio.setStyleName(BaseTheme.BUTTON_LINK);
		deletePortfolio.setDescription("Delete Portfolio");
		deletePortfolio.setIcon(new ThemeResource(
				"../runo/icons/16/document-delete.png"));
		deletePortfolio.setEnabled(false);
		menubar.addComponent(deletePortfolio);

		portfolioTree = new Tree("Portfolio");
		portfolioTree.setImmediate(true);
		rootlayout.addComponent(portfolioTree);
		portfolioTree.addListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				{
					Portfolio folio = (Portfolio) event.getItem()
							.getItemProperty(PORTFOLIO_PROPERTY_VALUE)
							.getValue();
					selectedPortfolio = folio;
					// make edit selected if
					if (folio.getPortfolioId() != null) {
						editPortfolioWindow.setEnabled(true);
						deletePortfolio.setEnabled(true);
					} else {
						editPortfolioWindow.setEnabled(false);
						deletePortfolio.setEnabled(false);

					}

					eventBus.post(new PortfolioSelectedEvent(selectedPortfolio));
				}

			}
		});
		portfolioContainer = new HierarchicalContainer();
		portfolioTree.setContainerDataSource(portfolioContainer);
		rootlayout.addComponent(portfolioTree);
		// Create containerproperty for name
		portfolioContainer.addContainerProperty(PORTFOLIO_PROPERTY_VALUE,
				Portfolio.class, null);
		portfolioContainer.addContainerProperty(PORTFOLIO_PROPERTY_NAME,
				String.class, null);
		// Create containerproperty for icon
		portfolioContainer.addContainerProperty(PORTFOLIO_PROPERTY_ICON,
				ThemeResource.class, new ThemeResource(
						"../runo/icons/16/document.png"));

		portfolioContainer.addContainerProperty(PORTFOLIO_PROPERTY_ICON,
				ThemeResource.class, new ThemeResource(
						"../runo/icons/16/document.png"));

		// Set tree to show the 'name' property as caption for items
		portfolioTree.setItemCaptionPropertyId(PORTFOLIO_PROPERTY_NAME);
		portfolioTree
				.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);

	}

	public void onLoad() {
		updateTree();
		portfolioTree.expandItemsRecursively(0);

		if (selectedPortfolio == null) {
			// fire set selected on tree root
			portfolioTree.select(0);
			selectedPortfolio = rootNode;
			eventBus.post(new PortfolioSelectedEvent(selectedPortfolio));
		}
	}

	private Window openWindow(Window window, String caption,
			SubComponentView view) {
		if (window == null) {
			window = new Window(caption);
			// ...and make it modal
			window.setModal(true);

			// addNewPortfolio.setStyleName(")
			// AddNewPortfolioViewImpl
			view.init();
			view.getView().setSizeUndefined();
		}
		if (window.getParent() != null) {
			// window is already showing
			// getWindow().showNotification("Window is already open");
		} else {
			// Open the subwindow by adding it to the parent
			// window
			window.setContent(view.getView());
			YuppiewallUI.getInstance().uiController.getWindow().addWindow(
					window);
			window.center();
		}
		return window;

	}

	private void addRoot() {
		// Add root
		portfolioCount = 0;
		allHolding = portfolioContainer.addItem(portfolioCount);
		rootNode = new Portfolio((PrimaryPrincipal) YuppiewallUI.getInstance()
				.getApplicationData("user"), null, "My Holding", null);
		allHolding.getItemProperty(PORTFOLIO_PROPERTY_NAME).setValue(
				rootNode.toString());
		allHolding.getItemProperty(PORTFOLIO_PROPERTY_VALUE).setValue(rootNode);

		// Allow children
		portfolioContainer.setChildrenAllowed(portfolioCount, true);
		portfolioCount += 1;
	}

	private void updateTree() {
		List<Portfolio> portfolios = eventHandeler.updateTreeView();
		portfolioContainer.removeAllItems();
		addRoot();
		for (Portfolio portfolio : portfolios) {
			Item item = portfolioContainer.addItem(portfolioCount);
			item.getItemProperty(PORTFOLIO_PROPERTY_NAME).setValue(
					portfolio.toString());
			item.getItemProperty(PORTFOLIO_PROPERTY_VALUE).setValue(portfolio);
			portfolioContainer.setParent(portfolioCount, 0);
			portfolioContainer.setChildrenAllowed(portfolioCount, false);
			portfolioCount += 1;
		}
	}

	@Override
	public void addListener(PortfolioTreeViewEvent eventHandeler) {
		this.eventHandeler = eventHandeler;
	}

	public com.vaadin.ui.Component getRoot() {
		return rootlayout;
	}
}
