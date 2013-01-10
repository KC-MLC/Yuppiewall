package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.vaadin.YuppiewallUI;

import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
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
public class PortfolioTreeViewImpl extends VerticalLayout implements
		PortfolioTreeView {

	private HierarchicalContainer portfolioContainer;

	private int portfolioCount;

	private Item allHolding;
	private Window addNewPortfolio;

	private PortfolioTreeViewEvent eventHandeler = new PortfolioTreeViewPresenter();

	public PortfolioTreeViewImpl() {
		HorizontalLayout menubar = new HorizontalLayout();
		menubar.setStyleName("small-segment");
		addComponent(menubar);

		Button addNewPortfolio = new Button("add");
		addNewPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
		addNewPortfolio.setDescription("add new Portfolio");
		addNewPortfolio.setIcon(new ThemeResource(
				"../runo/icons/16/document-add.png"));

		addNewPortfolio.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				openAddNewPortfolioWindow();

			}
		});

		menubar.addComponent(addNewPortfolio);
		Button editPortfolio = new Button("edit");
		editPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
		editPortfolio.setDescription("Edit Portfolio");
		editPortfolio
				.setIcon(new ThemeResource("../runo/icons/16/document.png"));

		menubar.addComponent(editPortfolio);
		editPortfolio.setEnabled(false);
		Button deletePortfolio = new Button("delete");
		deletePortfolio.setStyleName(BaseTheme.BUTTON_LINK);
		deletePortfolio.setDescription("Delete Portfolio");
		deletePortfolio.setIcon(new ThemeResource(
				"../runo/icons/16/document-delete.png"));
		deletePortfolio.setEnabled(false);
		menubar.addComponent(deletePortfolio);

		Tree portfolioTree = new Tree("Portfolio");
		addComponent(portfolioTree);

		portfolioContainer = new HierarchicalContainer();
		portfolioTree.setContainerDataSource(portfolioContainer);
		addComponent(portfolioTree);
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

		updateTree();

	}

	private void openAddNewPortfolioWindow() {
		if (addNewPortfolio == null) {
			addNewPortfolio = new Window("Add New Portfolio");
			// ...and make it modal
			addNewPortfolio.setModal(true);
			// addNewPortfolio.setStyleName(")
			// AddNewPortfolioViewImpl
		}
		if (addNewPortfolio.getParent() != null) {
			// window is already showing
			getWindow().showNotification("Window is already open");
		} else {
			// Open the subwindow by adding it to the parent
			// window
			addNewPortfolio.setContent(new AddNewPortfolioViewImpl());
			YuppiewallUI.getInstance().uiController.getWindow().addWindow(
					addNewPortfolio);
			addNewPortfolio.center();

		}

	}

	private void addRoot() {
		// Add root
		allHolding = portfolioContainer.addItem(portfolioCount);
		Portfolio all = new Portfolio(null, "all", "My Holding", null);
		allHolding.getItemProperty(PORTFOLIO_PROPERTY_NAME).setValue(
				all.toString());
		allHolding.getItemProperty(PORTFOLIO_PROPERTY_VALUE).setValue(all);

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
			portfolioContainer.setParent(0, portfolioCount);
			portfolioContainer.setChildrenAllowed(portfolioCount, false);
			portfolioCount += 1;
		}
	}

	@Override
	public void addListener(PortfolioTreeViewEvent eventHandeler) {
		this.eventHandeler = eventHandeler;

	}
}
