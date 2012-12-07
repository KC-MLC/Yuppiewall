package gabriel.yuppiewall.gwt.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.ImageResource;

public interface YuppieWallResources extends ClientBundle {

	@NotStrict
	@Source("YuppieWall.css")
	CssResource css();

	ImageResource loading();
}
