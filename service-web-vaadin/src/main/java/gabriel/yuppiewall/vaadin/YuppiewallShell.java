package gabriel.yuppiewall.vaadin;

import gabriel.yuppiewall.vaadin.application.Application;
import gabriel.yuppiewall.vaadin.application.ApplicationService;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
@Component("yuppiewallShell")
@Scope("prototype")
public class YuppiewallShell extends Window {

	private VerticalLayout sideBarMenu;
	private Panel contentArea;

	private String WINDOW_TITLE = "Title";

	private static final String THEME_NAME = "wall-midnight";

	public YuppiewallShell() {
	}

	// @PostConstruct
	public void init() {

		setCaption(WINDOW_TITLE);
		setName(WINDOW_TITLE);
		setTheme(THEME_NAME);
		setHeight("100%");
		setWidth("100%");
		setSizeFull();

		VerticalLayout mainLayout = new VerticalLayout();
		setContent(mainLayout);

		mainLayout.setSizeFull();

		HorizontalLayout headerLayout = new HorizontalLayout();
		mainLayout.addComponent(headerLayout);
		headerLayout.setHeight("30px");
		headerLayout.setWidth("100%");
		headerLayout.setSpacing(false);
		headerLayout.addComponent(new Label("11111111111111111"));

		HorizontalLayout mainSplit = new HorizontalLayout();
		mainSplit.setSizeFull();
		mainLayout.addComponent(mainSplit);
		mainLayout.setExpandRatio(mainSplit, 1);

		/*
		 * CssLayout bottom = new CssLayout(); bottom.setHeight("25px");
		 * //$NON-NLS-1$ bottom.setWidth("100%"); //$NON-NLS-1$
		 * //bottom.setStyleName("toolbar"); //$NON-NLS-1$
		 * mainLayout.addComponent(bottom);
		 * 
		 * bottom.addComponent(contactUS = new Label("BOTTOM")); //$NON-NLS-1$
		 */
		// Menu tree, initially shown

		Panel sideBarMenuH = new Panel();
		sideBarMenuH.setWidth("53px"); //$NON-NLS-1$
		sideBarMenuH.setHeight("100%");
		sideBarMenuH.setStyleName("sidebar");
		sideBarMenuH.setContent(new VerticalLayout());
		((VerticalLayout) sideBarMenuH.getContent()).setSpacing(false);
		((VerticalLayout) sideBarMenuH.getContent()).setMargin(false);

		sideBarMenu = new VerticalLayout();
		sideBarMenuH.addComponent(sideBarMenu);

		// sideBarMenu.setMargin(true);
		sideBarMenu.setWidth("53px"); //$NON-NLS-1$
		sideBarMenu.setSpacing(true);

		mainSplit.addComponent(sideBarMenuH);

		mainSplit.addComponent(contentArea = new Panel(new VerticalLayout()));
		contentArea.setHeight("100%");
		// contentArea.setStyleName("borderless bubble");
		mainSplit.setExpandRatio(contentArea, 1);
		contentArea.addComponent(new Label("nnnnenter"));
		((VerticalLayout) contentArea.getContent()).setMargin(true);
		load();
	}

	private void load() {
		YuppiewallUI ui = YuppiewallUI.getInstance();
		ApplicationService applicationService = ui.getApplicationService();
		@SuppressWarnings("rawtypes")
		List<Application> apps = applicationService.getApplications();

		for (Application<ComponentContainer> app : apps) {

			Button button = new Button();
			button.addListener(new OpenApp(app));
			button.setStyleName("borderless");
			button.setIcon(new ThemeResource(app.getThumbnail()));
			button.setDescription(app.getTitle());

			sideBarMenu.addComponent(button);

		}

	}

	class OpenApp implements Button.ClickListener {

		private Application<ComponentContainer> application;
		private Window subwindow;

		OpenApp(Application<ComponentContainer> application) {
			this.application = application;

		}

		@Override
		public void buttonClick(ClickEvent event) {
			if (subwindow == null) {
				subwindow = new Window(application.getTitle());
				// ...and make it modal
				subwindow.setModal(true);
				application.initialize();
			}
			if (subwindow.getParent() != null) {
				// window is already showing
				getWindow().showNotification("Window is already open");
			} else {
				// Open the subwindow by adding it to the parent
				// window
				subwindow.setContent(application.getApplicationUI());
				getWindow().addWindow(subwindow);
				subwindow.setWidth("90%");
				subwindow.setHeight("90%");
				application.onLoad();
			}
		}

	}
}