package tsxdk.query;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import common.defaults.SystemDescriptors;
import common.utility.Configuration;
import tsxdk.base.TSX;
import tsxdk.query.model.Query;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
        this.current = query;
        engine.deploy(query);
    }

    @Override
    public void deployAndSync(Query query) {
        deploy(query);
        await();
    }

    @Override
    public void deployAndWait(Query query, long milliseconds) {
        deploy(query);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            log.error("Wait after deploy has been interrupted", e);
        }
    }

    public Optional<Query.ResponseContainer> expect(Long maximumDelay) {
        try {
            final Stopwatch watch = Stopwatch.createStarted();
            if (!current.latchAwait(maximumDelay)) {
                return Optional.empty();
            } else {
                log.debug("Query took {} μs to yield a response", watch.elapsed(TimeUnit.MICROSECONDS));
                return Optional.of(current.getResponse());
            }
        } catch (InterruptedException e) {
            log.warn("Query-latch-await was interrupted", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Query.ResponseContainer> expect() {
        final Optional<Query.ResponseContainer> result = expect(cfg.QUERY_MAXIMUMRESPONSETIMEOUT);
        if (result.isPresent()) {
            return result;
        } else {
            log.warn("Response to Query ({}) took too long - interrupted", current.getQueryString());
            return Optional.empty();
        }
    }

    @Override
    public void await() {
        if (!expect(cfg.QUERY_MAXIMUMRESPONSETIMEOUT).isPresent()) {
            log.warn("Response to Query ({}) took too long - interrupted", current.getQueryString());
        }
    }

    @Override
    public void shutdown () {
        engine.shutdown();
    }
}
