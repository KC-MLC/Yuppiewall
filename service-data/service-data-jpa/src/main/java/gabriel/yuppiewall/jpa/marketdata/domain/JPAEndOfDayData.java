package gabriel.yuppiewall.jpa.marketdata.domain;

import gabriel.yuppiewall.common.Tuple;
import gabriel.yuppiewall.jpa.market.domain.JPAInstrument;
import gabriel.yuppiewall.marketdata.domain.EndOfDayData;
import gabriel.yuppiewall.scanner.domain.GlobalFilter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

@SuppressWarnings("serial")
@Entity
@Table(name = "end_of_day_data")
public class JPAEndOfDayData implements Serializable {

	@Id
	private String identifier;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "instrument", nullable = false, updatable = false)
	private JPAInstrument instrument;

	@Column(name = "trade_date")
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name = "stock_volume", precision = 14, nullable = false, updatable = false)
	private BigDecimal stockVolume;

	@Column(name = "stock_price_open", precision = 14, scale = 2, nullable = false, updatable = false)
	private BigDecimal stockPriceOpen;

	@Column(name = "stock_price_high", precision = 14, nullable = false, updatable = false)
	private BigDecimal stockPriceHigh;
	@Column(name = "stock_price_low", precision = 14, nullable = false, updatable = false)
	private BigDecimal stockPriceLow;
	@Column(name = "stock_price_close", precision = 14, nullable = false, updatable = false)
	private BigDecimal stockPriceClose;
	@Column(name = "stock_price_adj_close", precision = 14, nullable = false, updatable = false)
	private BigDecimal stockPriceAdjClose;

	public JPAEndOfDayData() {

	}

	public JPAEndOfDayData(EndOfDayData eodd) {

		this.date = eodd.getDate();
		this.stockPriceAdjClose = eodd.getStockPriceAdjClose();
		this.stockPriceClose = eodd.getStockPriceClose();
		this.stockPriceHigh = eodd.getStockPriceHigh();
		this.stockPriceLow = eodd.getStockPriceLow();
		this.stockPriceOpen = eodd.getStockPriceOpen();
		this.stockVolume = eodd.getStockVolume();
		this.instrument = new JPAInstrument(eodd.getInstrument());

		this.identifier = eodd.getInstrument().getExchange().getSymbol()
				+ eodd.getInstrument().getSymbol() + eodd.getDate().getTime();
	}

	public EndOfDayData getEndOfDayData() {
		return new EndOfDayData(instrument.getInstrument(), date,
				stockPriceOpen, stockPriceHigh, stockPriceLow, stockPriceClose,
				stockVolume, stockPriceAdjClose);
	}

	static class EndOfDayDataSpecifications {
		public static Specification<JPAEndOfDayData> searchEndOfDayData(
				final GlobalFilter globalFilter) {
			return new Specification<JPAEndOfDayData>() {
				@SuppressWarnings("rawtypes")
				public Predicate toPredicate(Root eoddata, CriteriaQuery query,
						CriteriaBuilder builder) {
					Predicate predicate = builder.conjunction();
					Tuple<String, String> groupFilter = globalFilter
							.getGroup();
					if (groupFilter.getKey().equals("country")) {

					}

					return predicate;
				}
			};
		}
	}
}