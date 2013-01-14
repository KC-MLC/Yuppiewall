package gabriel.yuppiewall.feed.news;

import gabriel.yuppiewall.instrument.domain.Instrument;
import gabriel.yuppiewall.trade.service.AccountManager;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

import java.util.ArrayList;
import java.util.List;

public abstract class NewsReaperImpl implements NewsReaper {

	@Override
	public List<Feed> findNewsOfMyholdings(PrimaryPrincipal user) {

		List<Feed> feeds = new ArrayList<>();

		// aggregate holdings
		List<Instrument> holdings = getAccountManager().getAllInstrument(user);
		// loop through news agency
		// create csv key word list
		StringBuilder keyWordList = new StringBuilder();
		for (Instrument instrument : holdings) {
			keyWordList = keyWordList.append(instrument.getSymbol() + ",");
		}
		if (keyWordList.length() == 0)
			return feeds;
		String csv = keyWordList.substring(0, keyWordList.length() - 1);

		List<NewsAgency> agency = getNewsAgencies();
		if (agency.size() == 0)
			return feeds;
		// create list
		if (agency.size() == 1)
			return agency.get(0).findNews(csv);

		for (NewsAgency newsAgency : agency) {

			List<Feed> newsList = newsAgency.findNews(csv);
			feeds.addAll(newsList);
		}

		return feeds;
	}

	protected abstract List<NewsAgency> getNewsAgencies();

	protected abstract AccountManager getAccountManager();

}
