package tsxdk.query.model.wrapper;


import tsxdk.query.model.QueryResultSet;

import java.util.Map;
import java.util.stream.Stream;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 27. of Mai
 *  2016
 *  10:22
 */
public class MultiEntityResponseDecorator extends QueryResponseDecorator {
    private final QueryResultSet resultSet;

    public MultiEntityResponseDecorator(QueryResultSet queryResponse) {
        super(queryResponse);
        this.resultSet = queryResponse;
    }

    public Map<String, String> getEntity(int idx) {
        return resultSet.getTable().row(idx);
    }

    public Stream<Map.Entry<Integer, Map<String, String>>> stream() {
        return resultSet.getTable().rowMap().entrySet().stream();
    }
}
