package gabriel.yuppiewall.feed.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleNews implements NewsAgency {

	private static final String URL_PREFIX = "https://news.google.com/news/feeds?q=";
	private static final String URL_SUFIX = "&output=rss";

	@Override
	public List<Feed> findNews(String csv) {
		List<Feed> feedList = new ArrayList<>();
		String[] kewWords = csv.split(",");
		for (String kewWord : kewWords) {
			String feedUrl = URL_PREFIX + kewWord + URL_SUFIX;
			try {
				List<Feed> list = RSSFeedParser.readFeed(feedUrl);
				feedList.addAll(list);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return feedList;
	}

}
