package gabriel.yuppiewall.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class YuppieWall implements EntryPoint {

	public static final YuppieWallResources images = GWT
			.create(YuppieWallResources.class);

	public static final String THEME = "clean";

	/**
	 * The main application shell.
	 */
	private YuppieWallShell shell;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Inject global styles.
		injectThemeStyleSheet();
		images.css().ensureInjected();

		// Initialize the constants.
		// YuppieWallConstants constants =
		// GWT.create(YuppieWallConstants.class);

		shell = new YuppieWallShell();
		RootLayoutPanel.get().add(shell);

	}

	/**
	 * Convenience method for getting the document's head element.
	 * 
	 * @return the document's head element
	 */
	private native HeadElement getHeadElement() /*-{
		return $doc.getElementsByTagName("head")[0];
	}-*/;

	/**
	 * Inject the GWT theme style sheet based on the RTL direction of the
	 * current locale.
	 */
	private void injectThemeStyleSheet() {
		// Choose the name style sheet based on the locale.
		String styleSheet = "gwt/" + THEME + "/" + THEME;
		styleSheet += LocaleInfo.getCurrentLocale().isRTL() ? "_rtl.css"
				: ".css";

		// Load the GWT theme style sheet
		String modulePath = GWT.getModuleBaseURL();
		LinkElement linkElem = Document.get().createLinkElement();
		linkElem.setRel("stylesheet");
		linkElem.setType("text/css");
		linkElem.setHref(modulePath + styleSheet);
		getHeadElement().appendChild(linkElem);
	}
}