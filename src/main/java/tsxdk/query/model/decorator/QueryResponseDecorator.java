package tsxdk.query.model.decorator;

import com.teamspeak.skymaster.tscontroller.tsmanagement.query.model.QueryResponse;

/**
 * Created by Ulli Gerhard on 04.11.2015.
 */
//unused - maybe remove ?
public abstract class QueryResponseDecorator implements QueryResponse {
    protected QueryResponseDecorator(QueryResponse queryResponse) {
    }
}
