package tsxdk.query;

import com.google.inject.Inject;
import common.defaults.SystemDescriptors;
import tsxdk.base.TSX;
import tsxdk.io.IO;
import tsxdk.query.model.Query;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 27. of Mai
 *  2016
 *  10:22
 */
public class QueryEngineImpl extends TSX implements QueryEngine  {
    private final IO tsIO;
    private final ScheduledThreadPoolExecutor executor;
    private final QueryResponseHandler responseHandler;
    private final ConcurrentLinkedQueue<Query> deployedQueries = new ConcurrentLinkedQueue<>();

    private final int deployDelay = 1000 / cfg.QUERY_PERSEC;
    private long lastDeploy;

    @Inject
    public QueryEngineImpl(IO tsIO) {
        super(SystemDescriptors.QUERY, QueryEngine.class);
        log.info("Starting");
        this.tsIO = tsIO;
        this.responseHandler = new QueryResponseHandler(this);
        log.info("Discarding startup messages");
        discardTeamspeakBootMessages();
        executor = new ScheduledThreadPoolExecutor(1);
        final int rcvPeriod = cfg.QUERY_RECVPERIOD;
        executor.scheduleAtFixedRate(responseHandler, 0L, rcvPeriod, TimeUnit.MILLISECONDS);
        log.info("Response-handler started with a period of {} ms", rcvPeriod);
        log.info("Running");
    }

    private void discardTeamspeakBootMessages() {
        String cur;
        do {
            cur = tsIO.in().orElse("");
            try {
                Thread.sleep(cfg.QUERY_RECVPERIOD);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("Discarded ({})", cur);
        } while (!cur.startsWith("Welcome"));
    }

    @Override
    public synchronized void deploy(Query query) {
        restrainQueryLoad();
        registerDeployedQuery(query);
        tsIO.out(query.getQueryString());
    }

    private void restrainQueryLoad() {
        final long currentTime = System.currentTimeMillis();
        final long difference = currentTime - lastDeploy;
        final long suspend = deployDelay - difference;

        if (suspend > 0) try {
            Thread.sleep(suspend);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void registerDeployedQuery(Query query) {
        deployedQueries.add(query);
        query.setDeployed();
        this.lastDeploy = query.getDeployStamp();
    }

    @Override
    public IO getIO() {
        return tsIO;
    }

    @Override
    public Query getCurrentQuery() {
        return deployedQueries.peek();
    }

    @Override
    public void notifyQuerySatisfied() {
        deployedQueries.remove();
    }

/*  This has been deprecated in favor of query-specific-synchronization
    @Override
    public void waitForCompletion() {
        log.debug("Waiting for query-completion");
        final int completionPeriod = Configuration.QUERY_COMPLETEPERIOD;
        final int completionTicks = Configuration.QUERY_COMPLETIONTICKS;
        int ticks = 0;
        while (!deployedQueries.isEmpty()) try {
            Thread.sleep(completionPeriod);
            ticks++;
            if (ticks >= completionTicks) {
                handleDeadQueries();
            }
        } catch (InterruptedException e) {
            log.warn("Query-completion-sleep interrupted");
        }
        log.debug("Waited {} ms for query-completion", completionPeriod * ticks);
    }*/

    @Override
    public long getTimeSinceLastDeploy() {
        return System.currentTimeMillis() - lastDeploy;
    }

    private void handleDeadQueries() {
        log.warn("Query-timeout, removing {} queries", deployedQueries.size());
        for (Query query : deployedQueries) {
            log.debug("Unanswered Query: {}, Response is {}, Error is {}", query.getQueryString(), query.getResult(), query.getError());
        }
        deployedQueries.clear();
    }
}
