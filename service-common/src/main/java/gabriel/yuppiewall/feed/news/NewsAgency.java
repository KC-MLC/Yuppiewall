package gabriel.yuppiewall.feed.news;

import java.util.List;

public interface NewsAgency {

	List<Feed> findNews(String csv);

}
