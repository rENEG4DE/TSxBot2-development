package tsxdk.query;

import tsxdk.query.model.Query;
import tsxdk.query.model.QueryResponse;

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
    Optional<QueryResponse> expect (Long maxDelay);
}