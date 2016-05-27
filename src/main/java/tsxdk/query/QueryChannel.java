package tsxdk.query;

import tsxdk.query.model.Query;

import java.util.Optional;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 25. of Mai
 * 2016
 * 07:12
 */
public interface QueryChannel {
    void deploy (Query query);
    void await();
    void deployAndSync(Query query);
    void deployAndWait(Query query, long milliseconds);
    Optional<Query.ResponseContainer> expect (Long maxDelay);
    Optional<Query.ResponseContainer> expect ();

    void shutdown();
}