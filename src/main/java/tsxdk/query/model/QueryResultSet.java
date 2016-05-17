package tsxdk.query.model;

import com.google.common.collect.Table;

/**
 * Created by Ulli Gerhard on 14.10.2015.
 */
public class QueryResultSet implements QueryResponse {
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(QueryResultSet.class);
    private final Table<Integer, String, String> table;

    public QueryResultSet(Table<Integer, String, String> content) {
        this.table = content;
    }

    public Table<Integer, String, String> getTable() {
        return table;
    }

    @Override
    public String toString() {
        return "QueryResultSet{" +
                "table=" + table +
                '}';
    }
}