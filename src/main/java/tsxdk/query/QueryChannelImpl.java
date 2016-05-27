package tsxdk.query;

import com.google.inject.Inject;
import common.defaults.SystemDescriptors;
import tsxdk.base.TSX;
import tsxdk.query.model.Query;
import tsxdk.query.model.QueryResponse;

import java.util.Optional;

/**
 * TSxBot2
 * Coded by rENEG4DE
 * on 25. of Mai
 * 2016
 * 06:54
 */
public class QueryChannelImpl extends TSX implements QueryChannel {
    private final QueryEngine engine;

    private Query current;

    @Inject
    public QueryChannelImpl(QueryEngine engine) {
        super(SystemDescriptors.QUERY, QueryChannel.class);
        this.engine = engine;
    }

    @Override
    public void deploy(Query query) {
        engine.deploy(query);
        this.current = query;
}

    @Override
    public Optional<QueryResponse> expect(Long maxDelay) {
        try {
            if (!current.latchAwait(maxDelay)) {
                return Optional.empty();
            } else {
                return Optional.of(current.getError());
            }
        } catch (InterruptedException e) {
            log.warn("Query-latch-await was interrupted", e);
            return Optional.empty();
        }
    }
}
