package com.emc.traceloader.tracecollector.unit.synchronization.service.impl;

import com.emc.traceloader.tracecollector.unit.httputils.HttpUtils;
import com.emc.traceloader.tracecollector.unit.synchronization.service.SynchronizationService;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class SynchronizationServiceImpl implements SynchronizationService {

    private static final Logger logger = Logger.getLogger(SynchronizationServiceImpl.class.getName());

    private Map<String, ScheduledFuture<?>> syncThreads = new HashMap<String, ScheduledFuture<?>>();
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    @Override
    public void startSynchronization(String syncid, Integer heartbeatInterval, URL managerUrl) {
        logger.info("Requesting synchronization start with syncid = " + syncid);
        logger.info("Manager URL: " + managerUrl) ;
        SynchronizationThread thread = new SynchronizationThread(managerUrl, syncid);
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(thread, 0, heartbeatInterval, TimeUnit.MILLISECONDS);
        syncThreads.put(syncid, future);
    }

    @Override
    public void stopSynchronization(String syncid) {
        logger.info("Stopping synchronization for syncid: " + syncid);
        if(syncThreads.containsKey(syncid)) {
            ScheduledFuture<?> future = (ScheduledFuture<?>)syncThreads.get(syncid);
            future.cancel(true);
        }
    }

    private class SynchronizationThread implements Runnable {

        private String syncid;
        private URL managerUrl;

        private SynchronizationThread(URL url, String syncid) {
            this.syncid = syncid;
            this.managerUrl = url;
        }

        @Override
        public void run() {
            HttpUtils.sendHeartbeat(syncid, managerUrl);
        }
    }
}
