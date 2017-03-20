package com.tsxbot.system.core;

import com.google.inject.Inject;
import com.tsxbot.common.defaults.ClientSystemDescriptors;
import com.tsxbot.tsxdk.query.QueryChannel;
import com.tsxbot.tsxdk.query.QueryGateway;
import com.tsxbot.tsxdk.query.engine.QueryFactory;

/*
 * TSxBot2
 * Coded by rENEG4DE
 * on 29. of Mai
 * 2016
 * 09:21
 */
public class Future_test_Core extends BaseCore {

    @Inject
    public Future_test_Core() {
        super(ClientSystemDescriptors.CORE, Future_test_Core.class);
        log.info("Future_test_Core created");
    }

    protected void doStuff() {
        try {
            final QueryGateway queryGateway = obtainQueryGateway();
            final QueryChannel queryChannel = queryGateway.getQueryChannel();
            final QueryFactory queryFactory = queryGateway.getQueryFactory();

            queryGateway.login();
            queryGateway.use(1);

            //Pump all queries to ts-server, no delays except cfg.QUERY_PERSEC
//            {
//                queryChannel.deploy(queryFactory.channellist());
//                queryChannel.deploy(queryFactory.clientlist());
//                queryChannel.deploy(queryFactory.channellist());
//                queryChannel.deploy(queryFactory.clientlist());
////                final Future<Query.ResponseContainer> ftr = queryChannel.deployGetFuture(queryFactory.clientlist());
////
////                while (!ftr.isDone()) {
////                    log.info("Future is not here yet");
////                    Thread.sleep(100);
////                }
//                queryChannel.deploy(queryFactory.channellist());
//                queryChannel.deploy(queryFactory.clientlist());
//                queryChannel.deploy(queryFactory.channellist());
//                queryChannel.deploy(queryFactory.clientlist());
////                log.info("Future {}", ftr.get());
//            }

//            {
//                queryChannel.deployGetFuture(queryFactory.channellist());
//                queryChannel.deployGetFuture(queryFactory.clientlist());
//                queryChannel.deployGetFuture(queryFactory.channellist());
//                queryChannel.deployGetFuture(queryFactory.clientlist());
//                final Future<Query.ResponseContainer> ftr = queryChannel.deployGetFuture(queryFactory.clientlist());
//
//                while (!ftr.isDone()) {
//                    log.info("Future is not here yet");
//                    Thread.sleep(10);
//                }
//                queryChannel.deployGetFuture(queryFactory.channellist());
//                queryChannel.deployGetFuture(queryFactory.clientlist());
//                queryChannel.deployGetFuture(queryFactory.channellist());
//                queryChannel.deployGetFuture(queryFactory.clientlist());
//                log.info("Future {}", ftr.get());
//            }

            queryGateway.shutdown();
        } catch (Exception e) {
            log.error("Something happened that was not supposed to", e);
        }

        log.info("Future_test_Core suspended");
    }
}
