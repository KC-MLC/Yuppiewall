package gabriel.yuppiewall.vaadin.application.portfolio;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
class PortfolioTreeView extends VerticalLayout {

	PortfolioTreeView() {
		HorizontalLayout menubar = new HorizontalLayout();
		menubar.setStyleName("small-segment");
		addComponent(menubar);

		Button addNewPortfolio = new Button("add");
		addNewPortfolio.setStyleName(BaseTheme.BUTTON_LINK);
		addNewPortfolio.setDescription("add new Portfolio");
		addNewPortfolio.setIcon(new ThemeResource(
				"../runo/icons/16/document-add.png"));

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
	}
}
