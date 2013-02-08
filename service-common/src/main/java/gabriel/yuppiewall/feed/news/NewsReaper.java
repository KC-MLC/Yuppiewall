package gabriel.yuppiewall.feed.news;

import java.util.List;

import gabriel.yuppiewall.um.domain.PrimaryPrincipal;

public interface NewsReaper {

	List<Feed> findNewsOfMyholdings(PrimaryPrincipal user);

}
