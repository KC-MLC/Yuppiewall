package gabriel.yuppiewall.gwt.common.application.portfolio;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PortfolioSummary implements Serializable {

	private String name;
	private long total;
	private long todayChange;

	public PortfolioSummary() {

	}

	public PortfolioSummary(String name, long total) {
		this.name = name;
		this.total = total;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}