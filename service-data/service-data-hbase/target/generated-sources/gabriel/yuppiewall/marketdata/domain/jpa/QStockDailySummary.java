package gabriel.yuppiewall.marketdata.domain.jpa;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QStockDailySummary is a Querydsl query type for StockDailySummary
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QStockDailySummary extends EntityPathBase<StockDailySummary> {

    private static final long serialVersionUID = -1370182006;

    public static final QStockDailySummary stockDailySummary = new QStockDailySummary("stockDailySummary");

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

    public final StringPath exchange = createString("exchange");

    public final StringPath id = createString("id");

    public final NumberPath<java.math.BigDecimal> stockPriceAdjClose = createNumber("stockPriceAdjClose", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> stockPriceClose = createNumber("stockPriceClose", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> stockPriceHigh = createNumber("stockPriceHigh", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> stockPriceLow = createNumber("stockPriceLow", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> stockPriceOpen = createNumber("stockPriceOpen", java.math.BigDecimal.class);

    public final StringPath stockSymbol = createString("stockSymbol");

    public final NumberPath<java.math.BigInteger> stockVolume = createNumber("stockVolume", java.math.BigInteger.class);

    public QStockDailySummary(String variable) {
        super(StockDailySummary.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QStockDailySummary(Path<? extends StockDailySummary> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QStockDailySummary(PathMetadata<?> metadata) {
        super(StockDailySummary.class, metadata);
    }

}

