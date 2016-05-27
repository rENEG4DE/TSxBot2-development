package tsxdk.query.model;

import java.util.function.Consumer;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 27. of Mai
 * 2016
 * 09:30
 */
public final class ResponseAction {
    final Consumer<Query.ResponseContainer> responseAction;

    public ResponseAction(Consumer<Query.ResponseContainer> consumer) {
        responseAction = consumer;
    }

    void triggerResponseAction(Query query) {
        responseAction.accept(query.getResponse());
    }
}
