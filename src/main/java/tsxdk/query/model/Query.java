package tsxdk.query.model;


import tsxdk.query.model.wrapper.ErrorResponseWrapper;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 27. of Mai
 *  2016
 *  10:22
 */
public class Query {
    /*
            * A Response to a Query consists of
            *  * an optional resultSet
            *  * an error-message containinig msg and errorcode
            */
    final class ResponseContainer {
        private Optional<QueryResponse> resultSet;
        private ErrorResponseWrapper error;
    }

    private final String query;
    private final ResponseContainer responseContainer = new ResponseContainer();
    private Optional<ResponseAction> responseAction;

    private long deployStamp;
    private CountDownLatch latch;

    public ResponseContainer getResponse() {
        return responseContainer;
    }

    private Query() {
        query = "[YOU_CAN_NOT_SEE_THIS_EVER]";
    }

    public Query(String query) {
        this(query, null);
    }

    public Query(String query, Consumer<ResponseContainer> responseConsumer) {
        this.query = query;
        bindResponseAction(responseConsumer);
    }

    public String getQueryString() {
        return query;
    }

    public QueryResultSet getResult() {
        return (QueryResultSet) responseContainer.resultSet.get();
    }

    public ErrorResponseWrapper getError() {
        return responseContainer.error;
    }

    void setResult(QueryResponse result) {
        this.responseContainer.resultSet = Optional.ofNullable(result);
    }

    public void setError(ErrorResponseWrapper error) {
        this.responseContainer.error = error;
        triggerResponseAction();
    }

    public void bindResponseAction(Consumer<ResponseContainer> responseConsumer) {
        if (Objects.nonNull(responseConsumer)) {
            responseAction = Optional.of(new ResponseAction(responseConsumer));
        }
    }

    private void triggerResponseAction () {
        if (responseAction.isPresent()) {
            responseAction.get().triggerResponseAction(this);
        }
    }

    public boolean isSatisfied() {
        return Objects.nonNull(responseContainer.error);
    }

    public void setDeployed () {
        setDeployLatch();
        setDeployStamp();
    }

    public void setDeployStamp() {
        deployStamp = System.currentTimeMillis();
    }

    public void setDeployLatch() {
        this.latch = new CountDownLatch(1);
    }

    public long getDeployStamp() {
        return deployStamp;
    }

    public boolean latchAwait(Long maxDelay) throws InterruptedException {
        return latch.await(maxDelay, TimeUnit.MILLISECONDS);
    }
}