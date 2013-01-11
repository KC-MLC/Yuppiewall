package gabriel.yuppiewall.spring.feed.news;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gabriel.yuppiewall.feed.news.GoogleNews;
import gabriel.yuppiewall.feed.news.NewsAgency;
import gabriel.yuppiewall.feed.news.NewsReaperImpl;
import gabriel.yuppiewall.trade.service.AccountManager;

@Service("newsReaper")
public class SpringNewsReaper extends NewsReaperImpl {

	private List<NewsAgency> newsAgencies;
	@Autowired
	private AccountManager accountManager;

	@PostConstruct
	private void init() {
		newsAgencies = new ArrayList<>();
		newsAgencies.add(new GoogleNews());
	}

	@Override
	protected List<NewsAgency> getNewsAgencies() {
		return newsAgencies;
	}

	@Override
	protected AccountManager getAccountManager() {
		return accountManager;
	}

}
