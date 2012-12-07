package gabriel.yuppiewall.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class YuppieWallShell extends ResizeComposite {
	interface YuppieWallShellUiBinder extends UiBinder<Widget, YuppieWallShell> {
	}

	private static YuppieWallShellUiBinder uiBinder = GWT
			.create(YuppieWallShellUiBinder.class);

	private final String loadingHtml;

	public YuppieWallShell() {
		AbstractImagePrototype proto = AbstractImagePrototype
				.create(YuppieWall.images.loading());
		loadingHtml = proto.getHTML();

		// Initialize the ui binder.
		initWidget(uiBinder.createAndBindUi(this));

	}
}
