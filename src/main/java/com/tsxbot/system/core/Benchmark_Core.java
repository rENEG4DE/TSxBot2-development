package com.tsxbot.system.core;

import com.google.common.base.Stopwatch;
import com.tsxbot.common.defaults.ClientSystemDescriptors;
import com.tsxbot.tsxdk.client.entity.gateway.ChannelGateway;
import com.tsxbot.tsxdk.query.QueryChannel;
import com.tsxbot.tsxdk.query.QueryGateway;
import com.tsxbot.tsxdk.query.engine.QueryFactory;
import com.tsxbot.tsxdk.query.model.Query;

import java.util.concurrent.TimeUnit;

/*
 * TSxBot2
 * Coded by rENEG4DE
 * on 29. of Mai
 * 2016
 * 00:00
 *
 * Since querying TS is throttled by default,
 * these property/value combination needs to be set:
 *
 * query.receiverPeriod = 1
 * query.completionPeriod = 1
 * query.perSecond = 20000      //or how much queries you think you can reach..
 *
 * and
 *
 * log4j.rootLogger=INFO, stdout
 *
 * to avoid tons of debug-output
 */
public class Benchmark_Core extends BaseCore {
    final QueryGateway queryGateway;
    private final QueryFactory queryFactory;
    private final QueryChannel queryChannel;

    public Benchmark_Core() {
        super(ClientSystemDescriptors.CORE, Benchmark_Core.class);
        queryGateway = obtainQueryGateway();
        queryFactory = queryGateway.getQueryFactory();
        queryChannel = queryGateway.getQueryChannel();
        log.info("Benchmark_Core created");
    }

    public static BaseCore startCore() {
        return new Benchmark_Core();
    }

    protected void doStuff() {
        benchCheckVar();
        benchSetup();
        benchStage1();
        benchStage2(10, 1);
        benchStage2(10, 11);
        benchStage2(10, 21);
        benchStage2(10, 31);
        benchStage2(10, 41);
        benchClose();

        log.info("Benchmark_Core suspended");
    }

    private void benchCheckVar() {
        if (cfg.QUERY_RECVPERIOD > 5 || cfg.QUERY_PERSEC < 20000)
            log.warn("Suboptimal benchmark-configuration-environment");
    }

    private void benchSetup() {
        log.info("Setting up Benchmark");
        Query query = queryFactory.login(cfg.TSSERVER_LOGIN, cfg.TSSERVER_PASSWORD);
        queryChannel.deploy(query);
        query = queryFactory.use(1);
        queryChannel.deployAndWait(query, 20);
        purgeExistingChannels();
        log.info("Ready to Benchmark");
    }

    private void purgeExistingChannels() {
        try {
            final ChannelGateway channelListGateway = new ChannelGateway(queryGateway);
            for (int i : channelListGateway.getChannelIds()) {
                final Query channeldelete = queryFactory.channeldelete(i);
                queryChannel.deploy(channeldelete);
            }
        } catch (Exception e) {
            log.error("something happened that was not supposed to", e);
        }
    }

    private void benchStage1() {
        try {
            Query query = null;
            final int repCount = 50;
            int i = repCount;
            log.info("Benchmarking with {} repetitions of a channel-list-query", repCount);
            log.info("Benchmark started");
            final Stopwatch watch = Stopwatch.createStarted();
            for (; i > 0; --i) {
                query = queryFactory.channellist();
                queryChannel.deployAndSync(query);
            }
            watch.stop();
            log.info("Benchmark done");
            log.info("Querying channellist {} times "
                            + "took {} milliseconds",
                    repCount,
                    watch.elapsed(TimeUnit.MILLISECONDS));

            log.info("This yields an average of {} ms/Query",
                    watch.elapsed(TimeUnit.MILLISECONDS) / repCount);

        } catch (Exception e) {
            log.error("Something happened that was not supposed to", e);
        }
    }

    private void benchStage2(int createCount, int channelCnt) {
        int channelCount = createCount;
        final Stopwatch watch = Stopwatch.createStarted();
        for (int i = channelCount; i > 0; --i) {
            final Query channelCreate = queryFactory.channelcreate(Integer.toString(i + channelCnt), "1234");
            queryChannel.deployAndSync(channelCreate);
        }
        watch.stop();
        log.info("Populating virtual-server-channel-list with {} channels took {} ms",
                channelCount,
                watch.elapsed(TimeUnit.MILLISECONDS));

        benchStage1();
    }

    private void benchClose() {
        cleanup();
        final Query query = queryFactory.logout();
        queryChannel.deployAndSync(query);
        queryChannel.shutdown();
    }

    private void cleanup() {
        purgeExistingChannels();
    }
}
