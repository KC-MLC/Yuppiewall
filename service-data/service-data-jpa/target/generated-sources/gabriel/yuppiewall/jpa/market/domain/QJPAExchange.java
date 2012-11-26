package gabriel.yuppiewall.jpa.market.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QJPAExchange is a Querydsl query type for JPAExchange
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QJPAExchange extends EntityPathBase<JPAExchange> {

    private static final long serialVersionUID = -1412406897;

    public static final QJPAExchange jPAExchange = new QJPAExchange("jPAExchange");

    public final StringPath name = createString("name");

    public QJPAExchange(String variable) {
        super(JPAExchange.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QJPAExchange(Path<? extends JPAExchange> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QJPAExchange(PathMetadata<?> metadata) {
        super(JPAExchange.class, metadata);
    }

}

