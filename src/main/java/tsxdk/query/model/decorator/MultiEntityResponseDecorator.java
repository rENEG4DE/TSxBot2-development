package tsxdk.query.model.decorator;


import tsxdk.query.model.QueryResultSet;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Ulli Gerhard on 04.11.2015.
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
