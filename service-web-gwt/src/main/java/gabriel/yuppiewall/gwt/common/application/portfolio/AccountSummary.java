package gabriel.yuppiewall.gwt.common.application.portfolio;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AccountSummary implements Serializable {

	private String currencyCode;
	private long total;
	private long todayChange;

	public AccountSummary() {
	}

	private PortfolioSummary[] portFolioSummary;

	public AccountSummary(String currencyCode, long total) {
		this.currencyCode = currencyCode;
		this.total = total;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public long getTotal() {
		return total;
	}

	public long getTodayChange() {
		return todayChange;
	}

	public PortfolioSummary[] getPortFolioSummary() {
		return portFolioSummary;
	}

	public void setPortFolioSummary(PortfolioSummary[] portFolioSummary) {
		this.portFolioSummary = portFolioSummary;
	}

}
