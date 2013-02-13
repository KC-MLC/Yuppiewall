package gabriel.yuppiewall.gwt.client.application;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationWidgetView extends ResizeComposite {

	interface ApplicationWidgetViewCSS extends CssResource {
		String applicationPanelStyle();

	}

	interface ApplicationWidgetViewUiBinder extends
			UiBinder<Widget, ApplicationWidgetView> {
	}

	private static ApplicationWidgetViewUiBinder uiBinder = GWT
			.create(ApplicationWidgetViewUiBinder.class);
	@UiField(provided = true)
	SimpleLayoutPanel applicationPanel;

	@UiField
	Element nameElem;
	@UiField
	ApplicationWidgetViewCSS style;

	public ApplicationWidgetView() {
		applicationPanel = new SimpleLayoutPanel();
		applicationPanel.setSize("100%", "100%");

		initWidget(uiBinder.createAndBindUi(this));
		style.ensureInjected();
		applicationPanel.setStyleName(style.applicationPanelStyle());
	}

	public void setApplication(Widget widget) {
		applicationPanel.setWidget(widget);

		widget.getElement().getStyle().setMarginLeft(10.0, Unit.PX);
		widget.getElement().getStyle().setMarginRight(10.0, Unit.PX);

	}

	public void setName(String text) {
		nameElem.setInnerText(text);
	}
}
