package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.vaadin.application.Application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.vaadinvisualizations.AnnotatedTimeLine;
import org.vaadin.vaadinvisualizations.AnnotatedTimeLineEntry;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class PortfolioApplication implements Application<ComponentContainer>,
		Serializable {

	public PortfolioApplication() {

	}

	public boolean initialize;
	private VerticalLayout applicationUI;
	@Autowired
	private TransactionViewImpl contentPaneSearchSection;
	@Autowired
	private PortfolioTreeViewImpl portfolioTreeView;

	@Autowired
	private NewsReaperViewImpl newsReaperView;

	@Override
	public void initialize() {
		// setMargin(true);
		// setSpacing(true);
		// make it fill the whole window
		if (initialize) {
			return;
		}
		applicationUI = new VerticalLayout();
		applicationUI.setSizeFull();
		HorizontalSplitPanel hsp = new HorizontalSplitPanel();
		hsp.setSplitPosition(30, Sizeable.UNITS_PERCENTAGE);

		applicationUI.addComponent(hsp);
		VerticalSplitPanel navBar = new VerticalSplitPanel();
		hsp.setFirstComponent(navBar);

		portfolioTreeView.init();
		navBar.setFirstComponent(portfolioTreeView.getRoot());
		navBar.setSplitPosition(30, Sizeable.UNITS_PERCENTAGE);

		newsReaperView.init();
		navBar.setSecondComponent(newsReaperView.getRoot());

		VerticalSplitPanel contentPane = new VerticalSplitPanel();
		contentPane.setSplitPosition(70, Sizeable.UNITS_PERCENTAGE);
		hsp.setSecondComponent(contentPane);
		contentPaneSearchSection.init();
		contentPane.setFirstComponent(contentPaneSearchSection.getRoot());

		{
			AnnotatedTimeLine atl = new AnnotatedTimeLine();
			atl.setOption("displayAnnotations", true);
			// atl.setOption("wmode", "window");
			atl.setOption("wmode", "opaque");

			atl.addLineLabel("Sold Pencils");
			atl.addLineLabel("Sold Pens");
			// a time line can have multiple entries as above 'Sold Pencils' and
			// 'Sold Pens'
			// for each distinct entry you have to set a value for each of the
			// above entries

			ArrayList<AnnotatedTimeLineEntry> timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

			timeLineEntries.add(new AnnotatedTimeLineEntry(30000, "", "")); // Sold
																			// Pencils
			timeLineEntries.add(new AnnotatedTimeLineEntry(40645, "", "")); // Sold
																			// Pens

			atl.add(new GregorianCalendar(2008, 0, 1), timeLineEntries);

			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

			timeLineEntries.add(new AnnotatedTimeLineEntry(14045, "", "")); // Sold
																			// Pencils
			timeLineEntries.add(new AnnotatedTimeLineEntry(20374, "", "")); // Sold
																			// Pens

			atl.add(new GregorianCalendar(2008, 0, 2), timeLineEntries);
			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

			timeLineEntries.add(new AnnotatedTimeLineEntry(55022, "", "")); // Sold
																			// Pencils
			timeLineEntries.add(new AnnotatedTimeLineEntry(50766, "", "")); // Sold
																			// Pens

			atl.add(new GregorianCalendar(2008, 0, 3), timeLineEntries);
			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

			timeLineEntries.add(new AnnotatedTimeLineEntry(75284, "", "")); // Sold
																			// Pencils
			timeLineEntries.add(new AnnotatedTimeLineEntry(14334,
					"Out of Stock", "Ran out of stock at 4pm")); // Sold Pens

			atl.add(new GregorianCalendar(2008, 0, 4), timeLineEntries);
			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();
			timeLineEntries.add(new AnnotatedTimeLineEntry(41476,
					"Bought Pens", "Bought 200k Pens")); // Sold Pencils
			timeLineEntries.add(new AnnotatedTimeLineEntry(66467, "", "")); // Sold
																			// Pens

			atl.add(new GregorianCalendar(2008, 0, 5), timeLineEntries);
			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();

			timeLineEntries = new ArrayList<AnnotatedTimeLineEntry>();
			timeLineEntries.add(new AnnotatedTimeLineEntry(33322,
					"Closed Shop", "Had enough of pencils business")); // Sold
																		// Pencils
			timeLineEntries.add(new AnnotatedTimeLineEntry(39463,
					"Pens look good", "Swapping to pens wholesale")); // Sold
																		// Pens

			atl.add(new GregorianCalendar(2008, 0, 6), timeLineEntries);

			atl.setSizeFull();
			contentPane.setSecondComponent(atl);
		}

		initialize = true;
	}

	@Override
	public String getName() {
		throw new UnsupportedOperationException("getName");
	}

	@Override
	public boolean isInitialize() {
		return initialize;
	}

	@Override
	public ComponentContainer getApplicationUI() {
		return applicationUI;
	}

	@Override
	public String getTitle() {
		return "My Portfolio";
	}

	@Override
	public String getID() {
		throw new UnsupportedOperationException("getID");
	}

	@Override
	public String getThumbnail() {
		return "../wall-midnight/icons/portfolio.png";
	}

	@Override
	public void onLoad() {
		contentPaneSearchSection.onLoad();
		portfolioTreeView.onLoad();
		newsReaperView.onLoad();

	}
}
