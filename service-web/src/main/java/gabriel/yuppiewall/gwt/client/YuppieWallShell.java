package gabriel.yuppiewall.gwt.client;

import gabriel.yuppiewall.gwt.client.scan.view.ScanEditorViewImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class YuppieWallShell extends ResizeComposite {
	interface YuppieWallShellUiBinder extends UiBinder<Widget, YuppieWallShell> {
	}

	private static YuppieWallShellUiBinder uiBinder = GWT
			.create(YuppieWallShellUiBinder.class);
	@UiField
	Button open;

	private final String loadingHtml;

	public YuppieWallShell() {
		AbstractImagePrototype proto = AbstractImagePrototype
				.create(YuppieWall.images.loading());
		loadingHtml = proto.getHTML();

		// Initialize the ui binder.
		initWidget(uiBinder.createAndBindUi(this));

		final DialogBox dialogBox = createDialogBox();/* new DialogBox(); */
		dialogBox.ensureDebugId("cwDialogBox");
		dialogBox.setText("TITIL");
		// dialogBox.setSize("500px", "500px");

		// Create a table to layout the content
		// dialogBox.setWidget(new ScanEditorViewImpl());
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);

		// simplePopup.setWidget(new ScanEditorViewImpl());
		open.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.center();
				dialogBox.show();
			}
		});

	}

	private DialogBox createDialogBox() {
		// Create a dialog box and set the caption text
		final DialogBox dialogBox = new DialogBox();
		dialogBox.ensureDebugId("cwDialogBox");
		dialogBox.setText("constants.cwDialogBoxCaption()");

		// Create a table to layout the content
		VerticalPanel dc = new VerticalPanel();
		dc.setSpacing(4);
		dc.setSize("1100px", "500px");
		dialogBox.setWidget(dc);
		ScanEditorViewImpl details = new ScanEditorViewImpl();
		dc.add(details);
		dc.setCellHorizontalAlignment(details,
				HasHorizontalAlignment.ALIGN_CENTER);

		/*// Add some text to the top of the dialog
		HTML details = new HTML("constants.cwDialogBoxDetails()");
		dialogContents.add(details);
		dialogContents.setCellHorizontalAlignment(details,
				HasHorizontalAlignment.ALIGN_CENTER);

		// Add an image to the dialog
		Label image = new Label("Showcase.images.jimmy()");
		dialogContents.add(image);
		dialogContents.setCellHorizontalAlignment(image,
				HasHorizontalAlignment.ALIGN_CENTER);

		// Add a close button at the bottom of the dialog
		Button closeButton = new Button("constants.cwDialogBoxClose()",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						dialogBox.hide();
					}
				});
		dialogContents.add(closeButton);
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			dialogContents.setCellHorizontalAlignment(closeButton,
					HasHorizontalAlignment.ALIGN_LEFT);

		} else {
			dialogContents.setCellHorizontalAlignment(closeButton,
					HasHorizontalAlignment.ALIGN_RIGHT);
		}*/

		// Return the dialog box
		return dialogBox;
	}

}
