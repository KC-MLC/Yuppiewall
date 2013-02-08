package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.marketdata.repository.SystemDataRepository;
import gabriel.yuppiewall.marketdata.service.MarketDataService;
import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.domain.Transaction;
import gabriel.yuppiewall.trade.service.AccountManager;
import gabriel.yuppiewall.vaadin.YuppiewallUI;
import gabriel.yuppiewall.vaadin.application.Application;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.vaadinvisualizations.AnnotatedTimeLine;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
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
	@Autowired
	private EventBus eventBus;

	private Panel atlPanel;
	private AnnotatedTimeLine atl;
	private VerticalSplitPanel contentPane;

	class PortfolioChangeRecorder {
		@Subscribe
		public void recordPortfolioChange(PortfolioSelectedEvent e) {
			Portfolio selectedPortfolio = (Portfolio) e.getSource();
			if (selectedPortfolio == null)
				return;

			loadATL(selectedPortfolio);
		}
	}

	@Override
	public void initialize() {
		// setMargin(true);
		// setSpacing(true);
		// make it fill the whole window
		if (initialize) {
			return;
		}
		eventBus.register(new PortfolioChangeRecorder());
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

		contentPane = new VerticalSplitPanel();
		contentPane.setSplitPosition(70, Sizeable.UNITS_PERCENTAGE);
		hsp.setSecondComponent(contentPane);
		contentPaneSearchSection.init();
		contentPane.setFirstComponent(contentPaneSearchSection.getRoot());

		// contentPane.setSecondComponent(atlPanel = new Panel());
		// atlPanel.setSizeFull();

		initialize = true;
	}

	public void loadATL(Portfolio selectedPortfolio) {
		AccountManager accountManager = YuppiewallUI.getInstance().getService(
				"accountManager");
		List<Transaction> list = accountManager
				.getTransactions(selectedPortfolio);
		Collection<Instrument> instrumentSet = new HashSet<>();
		SystemDataRepository sdr = YuppiewallUI.getInstance().getService(
				"SystemDataRepository");
		for (Transaction transaction : list) {
			Instrument instrument = sdr.getInstrument(transaction
					.getInstrument());
			instrumentSet.add(instrument);
		}

		MarketDataService mds = YuppiewallUI.getInstance().getService(
				"MarketDataService");
		Map<String, List<EndOfDayData>> value = mds.findAllEndOfDayData(
				instrumentSet, 255, 0);
		if (atl != null) {
			atlPanel.removeComponent(atl);
			atl = null;
		}

		atl = new ATLView().drawChart1(value);
		// atlPanel.addComponent(atl);
		contentPane.setSecondComponent(atl);

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
