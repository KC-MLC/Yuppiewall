package gabriel.yuppiewall.gwt.client.scan.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ScanEditorViewImpl extends Composite {

	private SplitLayoutPanel display;

	public ScanEditorViewImpl() {

		display = new SplitLayoutPanel(5);
		initWidget(display);
		setSize("100%", "100%");

		display.addWest(new Label("List or tree showing saved query"), 100);
		// Init search box
		DecoratorPanel searchArea = new DecoratorPanel();
		// searchArea.setSize("100%", "100%");

		VerticalPanel searchAreaVP = new VerticalPanel();
		searchAreaVP.setSize("100%", "100%");
		searchAreaVP.setSpacing(5);
		searchArea.setWidget(searchAreaVP);
		TextArea textArea = new TextArea();
		textArea.ensureDebugId("cwBasicText-textarea");
		textArea.setVisibleLines(5);
		searchAreaVP.add(textArea);
		HorizontalPanel buttonCluster = new HorizontalPanel();
		buttonCluster.setSpacing(1);
		searchAreaVP.add(buttonCluster);
		buttonCluster.add(new Button("save"));
		buttonCluster.add(new Button("validate"));
		buttonCluster.add(new Button("run"));

		display.addNorth(searchArea, 50);
		display.add(new Label("tab panel containing grid"));

	}

}
