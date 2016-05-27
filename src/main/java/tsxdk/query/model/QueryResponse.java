package tsxdk.query.model;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 27. of Mai
 *  2016
 *  10:22
 */
public interface QueryResponse {
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(QueryResponse.class);

    default void assignTo(Query qry) {
        log.debug("Assigning result ({}) to ({})", toString(), qry.getQueryString());
        qry.setResult(this);
    }
}
