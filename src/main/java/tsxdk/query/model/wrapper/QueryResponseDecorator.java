package tsxdk.query.model.wrapper;


import tsxdk.query.model.QueryResponse;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 27. of Mai
 *  2016
 *  10:22
 */

public abstract class QueryResponseDecorator implements QueryResponse {
    protected QueryResponseDecorator(QueryResponse queryResponse) {
    }
}
