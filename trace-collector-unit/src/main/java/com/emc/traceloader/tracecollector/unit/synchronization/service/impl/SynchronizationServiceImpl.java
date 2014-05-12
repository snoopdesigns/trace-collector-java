package com.emc.traceloader.tracecollector.unit.synchronization.service.impl;

import com.emc.traceloader.tracecollector.unit.httputils.HttpUtils;
import com.emc.traceloader.tracecollector.unit.synchronization.service.SynchronizationService;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class SynchronizationServiceImpl implements SynchronizationService {

    private static final Logger logger = Logger.getLogger(SynchronizationServiceImpl.class.getName());

    private Map<String, Runnable> syncThreads = new HashMap<String, Runnable>();
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    @Override
    public void startSynchronization(String syncid, Integer heartbeatInterval, URL managerUrl) {
        logger.info("Requesting synchronization start with syncid = " + syncid);
        logger.info("Manager URL: " + managerUrl) ;
        SynchronizationThread thread = new SynchronizationThread(managerUrl, syncid);
        executor.scheduleAtFixedRate(thread, 0, heartbeatInterval, TimeUnit.MILLISECONDS);
        syncThreads.put(syncid, thread);
    }

    @Override
    public void stopSynchronization(String syncid) {

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
