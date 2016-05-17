package tsxdk.query.model;

/**
 * Created by Ulli Gerhard on 14.10.2015.
 */
public interface QueryResponse {
    default void assignTo(Query qry) {
        org.slf4j.LoggerFactory.getLogger(QueryResponse.class).debug("Assigning result ({}) to ({})", toString(), qry.getQueryString());
        qry.setResult(this);
    }
}
