package tsxdk.query.model.wrapper;


import tsxdk.query.model.QueryResultSet;

import java.util.Map;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 27. of Mai
 *  2016
 *  10:22
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
