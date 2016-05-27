package tsxdk.query.model;


import tsxdk.query.model.wrapper.ErrorResponse;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 27. of Mai
 * 2016
 * 10:22
 */
public class Query {
    private final String query;
    private final ResponseContainer responseContainer = new ResponseContainer();
    private Optional<ResponseAction> responseAction;

    private long deployStamp;
    private CountDownLatch latch;

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

    void setResult(QueryResponse result) {
        this.responseContainer.resultSet = Optional.of(result);
    }

    public ErrorResponse getError() {
        return responseContainer.error;
    }

    public void setError(ErrorResponse error) {
        this.responseContainer.error = error;
        triggerResponseAction();
    }

    public ResponseContainer getResponse() {
        return responseContainer;
    }

    public void bindResponseAction(Consumer<ResponseContainer> responseConsumer) {
        if (Objects.nonNull(responseConsumer)) {
            responseAction = Optional.of(new ResponseAction(responseConsumer));
        } else {
            responseAction = Optional.empty();
        }
    }

    private void triggerResponseAction() {
        latch.countDown();
        if (responseAction.isPresent()) {
            responseAction.get().triggerResponseAction(this);
        }
    }

    public boolean isSatisfied() {
        return Objects.nonNull(responseContainer.error);
    }

    public void setDeployed() {
        setDeployLatch();
        setDeployStamp();
    }

    private void setDeployStamp() {
        deployStamp = System.currentTimeMillis();
    }

    private void setDeployLatch() {
        this.latch = new CountDownLatch(1);
    }

    public long getDeployStamp() {
        return deployStamp;
    }

    public boolean latchAwait(Long maxDelay) throws InterruptedException {
        return latch.await(maxDelay, TimeUnit.MILLISECONDS);
    }

    public final class ResponseContainer {
        private Optional<QueryResponse> resultSet;
        private ErrorResponse error;
    }

    private final class ResponseAction {
        private final Consumer<ResponseContainer> responseAction;

        public ResponseAction(Consumer<ResponseContainer> consumer) {
            responseAction = consumer;
        }

        void triggerResponseAction(Query query) {
            responseAction.accept(query.getResponse());
        }
    }
}