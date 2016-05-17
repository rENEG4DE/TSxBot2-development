package tsxdk.query;


import tsxdk.io.IO;
import tsxdk.query.model.Query;

/**
 * Created by Ulli Gerhard on 13.10.2015.
 */
public interface QueryEngine {
    void deploy(Query query);

    IO getIO();

    Query getCurrentQuery();

    void notifyQuerySatisfied();

    void waitForCompletion();

    long getTimeSinceLastDeploy();
}
