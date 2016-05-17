package tsxdk.query.model;

import com.teamspeak.skymaster.tscontroller.tsmanagement.query.model.decorator.ErrorResponseDecorator;

import java.util.Optional;
import java.util.function.Consumer;

class QueryResponseAction {
    private final Query query;
    Optional<Consumer<QueryResponse>> resultAction;
    Optional<Consumer<ErrorResponseDecorator>> errorAction;

    public QueryResponseAction(Query query) {
        this.query = query;
    }

    void triggerResultAction() {
        resultAction.ifPresent(action -> action.accept(query.getResult()));
    }

    void triggerErrorAction() {
        errorAction.ifPresent(action -> action.accept(query.getError()));
    }

    public void bindResultAction(Consumer<QueryResponse> responseAction) {
        resultAction = Optional.ofNullable(responseAction);
    }

    public void bindErrorAction(Consumer<ErrorResponseDecorator> responseAction) {
        errorAction = Optional.ofNullable(responseAction);
    }
}