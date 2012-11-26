package gabriel.yuppiewall.jpa.marketdata.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QJPAEndOfDayData is a Querydsl query type for JPAEndOfDayData
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QJPAEndOfDayData extends EntityPathBase<JPAEndOfDayData> {

    private static final long serialVersionUID = 434991350;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QJPAEndOfDayData jPAEndOfDayData = new QJPAEndOfDayData("jPAEndOfDayData");

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

    public final gabriel.yuppiewall.jpa.market.domain.QJPAExchange exchange;

    public final StringPath identifier = createString("identifier");

    public final NumberPath<java.math.BigDecimal> stockPriceAdjClose = createNumber("stockPriceAdjClose", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> stockPriceClose = createNumber("stockPriceClose", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> stockPriceHigh = createNumber("stockPriceHigh", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> stockPriceLow = createNumber("stockPriceLow", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> stockPriceOpen = createNumber("stockPriceOpen", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigInteger> stockVolume = createNumber("stockVolume", java.math.BigInteger.class);

    public final StringPath symbol = createString("symbol");

    public QJPAEndOfDayData(String variable) {
        this(JPAEndOfDayData.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QJPAEndOfDayData(Path<? extends JPAEndOfDayData> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QJPAEndOfDayData(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QJPAEndOfDayData(PathMetadata<?> metadata, PathInits inits) {
        this(JPAEndOfDayData.class, metadata, inits);
    }

    public QJPAEndOfDayData(Class<? extends JPAEndOfDayData> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exchange = inits.isInitialized("exchange") ? new gabriel.yuppiewall.jpa.market.domain.QJPAExchange(forProperty("exchange")) : null;
    }

}

