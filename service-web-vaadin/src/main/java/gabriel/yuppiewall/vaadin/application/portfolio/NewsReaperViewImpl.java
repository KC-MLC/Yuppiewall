package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.feed.news.Feed;
import gabriel.yuppiewall.feed.news.NewsReaper;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;
import gabriel.yuppiewall.vaadin.YuppiewallUI;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.google.common.eventbus.EventBus;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class NewsReaperViewImpl implements NewsReaperView, Serializable {
	private VerticalLayout rootlayout;

	@Autowired
	private EventBus eventBus;

	public NewsReaperViewImpl() {
	}

	public void init() {
		rootlayout = new VerticalLayout();
		rootlayout.setSpacing(true);

		updateNews();

	}

	private void updateNews() {

		NewsReaper newsReaper = YuppiewallUI.getInstance().getService(
				"newsReaper");

		List<Feed> feeds = newsReaper
				.findNewsOfMyholdings((PrimaryPrincipal) YuppiewallUI
						.getInstance().getApplicationData("user"));

		for (Feed feed : feeds) {

			Panel p = new Panel(feed.getTitle());
			VerticalLayout layout = (VerticalLayout) p.getContent();
			layout.setMargin(true); 
			rootlayout.addComponent(p);
			Label l = new Label(feed.getDescription());
			l.setContentMode(Label.CONTENT_XHTML);
			p.addComponent(l);

		}

	}

	public com.vaadin.ui.Component getRoot() {
		return rootlayout;
	}
}
