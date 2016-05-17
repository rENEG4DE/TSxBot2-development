package tsxdk.query.model;

import com.teamspeak.skymaster.tscontroller.tsmanagement.query.model.decorator.ErrorResponseDecorator;

import java.util.function.Consumer;

/**
 * Created by Ulli Gerhard on 08.10.2015.
 */
public class Query {
    private final String query;
    private final QueryResponseAction queryResponseAction = new QueryResponseAction(this);
    private ErrorResponseDecorator error;
    private QueryResponse result;
    private long deployStamp;

    private Query() {
        query = null;
    }

    public Query(String query) {
        this(query, null, null);
    }

    public Query(String query, Consumer<QueryResponse> resultAction, Consumer<ErrorResponseDecorator> errorAction) {
        this.query = query;
        bindResultAction(resultAction);
        bindErrorAction(errorAction);
    }

    public String getQueryString() {
        return query;
    }

    public QueryResultSet getResult() {
        return (QueryResultSet) result;
    }

    void setResult(QueryResponse result) {
        this.result = result;
        queryResponseAction.triggerResultAction();
    }

    public ErrorResponseDecorator getError() {
        return error;
    }

    public void setError(ErrorResponseDecorator error) {
        this.error = error;
        queryResponseAction.triggerErrorAction();
    }

    public void bindResultAction(Consumer<QueryResponse> responseAction) {
        queryResponseAction.bindResultAction(responseAction);
    }

    public void bindErrorAction(Consumer<ErrorResponseDecorator> responseAction) {
        queryResponseAction.bindErrorAction(responseAction);
    }

    public boolean isSatisfied() {
        return error != null;
    }

    public void setDeployStamp() {
        deployStamp = System.currentTimeMillis();
    }

    public long getDeployStamp() {
        return deployStamp;
    }
}