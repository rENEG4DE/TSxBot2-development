package com.tsxbot.system.core;

import com.tsxbot.common.defaults.ClientSystemDescriptors;
import com.tsxbot.tsxdk.query.QueryChannel;
import com.tsxbot.tsxdk.query.QueryGateway;
import com.tsxbot.tsxdk.query.engine.QueryFactory;
import com.tsxbot.tsxdk.query.model.Query;

import java.util.concurrent.Future;

/*
 * TSxBot2
 * Coded by rENEG4DE
 * on 29. of Mai
 * 2016
 * 09:21
 */
public class Future_test_Core extends BaseCore {

    public Future_test_Core() {
        super(ClientSystemDescriptors.CORE, Future_test_Core.class);
        log.info("Future_test_Core created");
    }

    protected void doStuff() {
        try {
            final QueryGateway queryGateway = obtainQueryGateway();
            final QueryChannel queryChannel = queryGateway.getQueryChannel();
            final QueryFactory queryFactory = queryGateway.getQueryFactory();

            Query query = queryFactory.login(cfg.TSSERVER_LOGIN, cfg.TSSERVER_PASSWORD);
            queryChannel.deploy(query);
            query = queryFactory.use(1);
            queryChannel.deployAndWait(query, 10);
            query = queryFactory.channellist();

            {
                final Future<Query.ResponseContainer> ftr = queryChannel.deployGetFuture(query);

//                while (!ftr.isDone()) {
//                    log.info("Future isDone: {}", ftr.isDone());
//                    Thread.sleep(2);
//                }
                log.info("Future {}", ftr.get());
            }


            query = queryFactory.logout();
            queryChannel.deployAndSync(query);

            queryChannel.shutdown();
        } catch (Exception e) {
            log.error("Something happened that was not supposed to", e);
        }

        log.info("Future_test_Core suspended");
    }
}
