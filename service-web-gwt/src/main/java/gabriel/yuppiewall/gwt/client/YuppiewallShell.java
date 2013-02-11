package gabriel.yuppiewall.gwt.client;

import gabriel.yuppiewall.gwt.client.application.ApplicationRegistry;
import gabriel.yuppiewall.gwt.client.application.ApplicationWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class YuppiewallShell extends ResizeComposite {
	interface YuppiewallShellUiBinder extends UiBinder<Widget, YuppiewallShell> {
	}

	private static YuppiewallShellUiBinder uiBinder = GWT
			.create(YuppiewallShellUiBinder.class);

	/**
	 * The panel that holds the content.
	 */
	@UiField
	SimpleLayoutPanel contentPanel;
	@UiField
	SimpleLayoutPanel mainMenuPanel;

	/**
	 * The current {@link Application} being displayed.
	 */
	private ApplicationWidget content;

	public YuppiewallShell(final ApplicationRegistry registry) {

		// Initialize the ui binder.
		initWidget(uiBinder.createAndBindUi(this));

		VerticalPanel menuPanel = new VerticalPanel();
		menuPanel.setSpacing(5);
		mainMenuPanel.add(menuPanel);
		{
			ToggleButton normalToggleButton = new ToggleButton(new Image(
					Yuppiewall.images.iconDashboard()));
			normalToggleButton
					.ensureDebugId("cwCustomButton-toggle-iconDashboard");
			menuPanel.add(normalToggleButton);
		}
		{
			ToggleButton normalToggleButton = new ToggleButton(new Image(
					Yuppiewall.images.iconPortfolio()));
			normalToggleButton
					.ensureDebugId("cwCustomButton-toggle-iconPortfolio");
			menuPanel.add(normalToggleButton);
			normalToggleButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Yuppiewall.HACK.displayAppicationWidget(registry
							.getAllApplicationWidgets().get(1)
							.getApplicationWidget());
				}
			});
		}
		{
			ToggleButton normalToggleButton = new ToggleButton(new Image(
					Yuppiewall.images.iconScaner()));
			normalToggleButton
					.ensureDebugId("cwCustomButton-toggle-iconScaner");
			menuPanel.add(normalToggleButton);
		}
		// Default to no content.
		contentPanel.ensureDebugId("contentPanel");
		setContent(null);
	}

	/**
	 * Set the content to display.
	 * 
	 * @param content
	 *            the content
	 */
	public void setContent(final ApplicationWidget content) {
		// Clear the old handler.

		this.content = content;
		if (content == null) {
			contentPanel.setWidget(null);
			return;
		}

		// Setup the options bar.

		// Show the widget.
		showApplication();
	}

	/**
	 * Show a example.
	 */
	private void showApplication() {
		if (content == null) {
			return;
		}

		contentPanel.setWidget(content);
	}

}
