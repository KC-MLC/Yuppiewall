package gabriel.yuppiewall.trade.domain;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Portfolio implements Serializable{

	private String portfolioId;
	private String portfolioName;
	
	private List<Transaction> transactions;
	

}
