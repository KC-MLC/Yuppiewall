package gabriel.yuppiewall.jpa.market.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QJPATradeDay is a Querydsl query type for JPATradeDay
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QJPATradeDay extends EntityPathBase<JPATradeDay> {

    private static final long serialVersionUID = -2130984188;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QJPATradeDay jPATradeDay = new QJPATradeDay("jPATradeDay");

    public final NumberPath<Integer> businessday = createNumber("businessday", Integer.class);

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

    public final QJPAExchange exchange;

    public final StringPath identifier = createString("identifier");

    public QJPATradeDay(String variable) {
        this(JPATradeDay.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QJPATradeDay(Path<? extends JPATradeDay> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QJPATradeDay(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QJPATradeDay(PathMetadata<?> metadata, PathInits inits) {
        this(JPATradeDay.class, metadata, inits);
    }

    public QJPATradeDay(Class<? extends JPATradeDay> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exchange = inits.isInitialized("exchange") ? new QJPAExchange(forProperty("exchange")) : null;
    }

}

