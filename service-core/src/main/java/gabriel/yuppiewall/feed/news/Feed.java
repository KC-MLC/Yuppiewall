package gabriel.yuppiewall.feed.news;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Feed implements Serializable {

	private String title;
	private String description;
	private String link;

	public Feed() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
