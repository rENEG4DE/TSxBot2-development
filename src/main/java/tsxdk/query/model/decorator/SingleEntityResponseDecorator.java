package tsxdk.query.model.decorator;


import tsxdk.query.model.QueryResultSet;

import java.util.Map;

/**
 * Created by Ulli Gerhard on 04.11.2015.
 */
public class SingleEntityResponseDecorator extends QueryResponseDecorator {
    private final Map<String, String> resultSet;

    public SingleEntityResponseDecorator(QueryResultSet queryResultSet) {
        super(queryResultSet);
        this.resultSet = queryResultSet.getTable().row(0);
    }

    public String get(String key) {
        return resultSet.get(key);
    }
}
