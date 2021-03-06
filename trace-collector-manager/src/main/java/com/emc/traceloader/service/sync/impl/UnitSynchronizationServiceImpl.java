package com.emc.traceloader.service.sync.impl;

import com.emc.traceloader.service.db.DatabaseService;
import com.emc.traceloader.service.db.impl.entity.Host;
import com.emc.traceloader.service.db.impl.entity.HostStatus;
import com.emc.traceloader.httputils.HttpUtils;
import com.emc.traceloader.service.sync.UnitSynchronizationService;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class UnitSynchronizationServiceImpl implements UnitSynchronizationService {

    private static final Logger logger = Logger.getLogger(UnitSynchronizationServiceImpl.class.getName());
    private static final Integer HEARTBEAT_INTERVAL = 10000;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Map<String, Runnable> syncThreads = new HashMap<String, Runnable>();
    private DatabaseService dbUtils;

    public UnitSynchronizationServiceImpl(DatabaseService dbUtils) {
        this.dbUtils = dbUtils;
    }

    @Override
    public boolean checkUnitAvailable(Host host) {
        URL unitUrl = null;
        try {
            unitUrl = new URL(HttpUtils.buildURLForSync(host, null, null, null));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return HttpUtils.checkURLAvailable(unitUrl);
    }

    @Override
    public String generateSyncId(Host host) {
        return this.makeSHA1Hash(host.getIp() + ":" + host.getPort());
    }

    @Override
    public void monitorSync(Host host, String userLogin) {
        try {
            logger.info("Starting monitoring for host: " + host);
            SyncMonitor monitor = new SyncMonitor(host, userLogin);
            syncThreads.put(host.getSyncid(), monitor);
            executor.submit(monitor);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stopMonitoring(Host host) {
        SyncMonitor monitor = (SyncMonitor)syncThreads.get(host.getSyncid());
        monitor.stop();
        logger.info("Monitoring stopped for syncid = " + host.getSyncid());
        try {
            HttpUtils.sendSyncRequest(new URL(HttpUtils.buildURLForSync(host, host.getSyncid(), null, "stop")));//here we need to maybe send request to unit to stop sending heartbeats
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        Iterator it = this.syncThreads.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            SyncMonitor monitor = (SyncMonitor)pairs.getValue();
            monitor.stop();
        }
        executor.shutdown();
    }

    private String makeSHA1Hash(String input) {
        String hexStr = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = input.getBytes();
            md.update(buffer);
            byte[] digest = md.digest();


            for (int i = 0; i < digest.length; i++) {
                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexStr;
    }

    public class SyncMonitor implements Runnable{

        private Host host;
        private String userLogin;
        private boolean stopped = false;

        public SyncMonitor(Host host, String userLogin) {
            this.host = host;
            this.userLogin = userLogin;
        }

        @Override
        public void run() {
            try {
                HttpUtils.sendSyncRequest(new URL(HttpUtils.buildURLForSync(host, host.getSyncid(), HEARTBEAT_INTERVAL, "start")));
                while(!stopped) {
                    try {
                        Thread.sleep(HEARTBEAT_INTERVAL / 2);
                        if(isWaitingTimeExceeded()) {
                            logger.info("Waiting time exceeded for host: " + host);
                            dbUtils.setHostStatus(host.getId(), userLogin, HostStatus.FATAL);
                            HttpUtils.sendSyncRequest(new URL(HttpUtils.buildURLForSync(host, host.getSyncid(), HEARTBEAT_INTERVAL, "start")));
                        } else {
                            dbUtils.setHostStatus(host.getId(), userLogin, HostStatus.OPERATIONAL);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private boolean isWaitingTimeExceeded() {
            boolean result = false;
            Date currentDate = new Date();
            Date dateOfLastHeartbeat = HeartbeatRegister.getInstance().getDateOfLastHeartbeat(host.getSyncid());
            if ((currentDate.getTime() - dateOfLastHeartbeat.getTime()) > HEARTBEAT_INTERVAL * 2) {
                result = true;
            }
            return result;
        }

        public void stop() {
            this.stopped = true;
        }
    }
}
