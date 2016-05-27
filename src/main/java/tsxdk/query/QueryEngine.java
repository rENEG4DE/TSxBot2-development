package tsxdk.query;


import tsxdk.io.IO;
import tsxdk.query.model.Query;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 27. of Mai
 *  2016
 *  10:22
 */
public interface QueryEngine {
    void deploy(Query query);

    IO getIO();

    Query getCurrentQuery();

    void notifyQuerySatisfied();

    long getTimeSinceLastDeploy();

    void shutdown();
}
