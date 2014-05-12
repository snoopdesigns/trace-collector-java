package com.emc.traceloader.sync.service.impl;

import com.emc.traceloader.db.DatabaseUtils;
import com.emc.traceloader.db.entity.Host;
import com.emc.traceloader.db.entity.HostStatus;
import com.emc.traceloader.httputils.HttpUtils;
import com.emc.traceloader.sync.service.UnitSynchronizationService;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class UnitSynchronizationServiceImpl implements UnitSynchronizationService {

    private static final Logger logger = Logger.getLogger(UnitSynchronizationServiceImpl.class.getName());
    private static final Integer HEARTBEAT_INTERVAL = 10000;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private DatabaseUtils dbUtils;

    public UnitSynchronizationServiceImpl(DatabaseUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    @Override
    public boolean checkUnitAvailable(Host host) {
        URL unitUrl = null;
        try {
            unitUrl = new URL(HttpUtils.buildURLForSync(host, null, null));
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
            executor.submit(new SyncMonitor(host, userLogin));
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        public SyncMonitor(Host host, String userLogin) {
            this.host = host;
            this.userLogin = userLogin;
        }

        @Override
        public void run() {
            try {
                HttpUtils.sendSyncRequest(new URL(HttpUtils.buildURLForSync(host, host.getSyncid(), HEARTBEAT_INTERVAL)));
                while(true) {
                    try {
                        Thread.sleep(HEARTBEAT_INTERVAL / 2);
                        if(isWaitingTimeExceeded()) {
                            logger.info("Waiting time exceeded for host: " + host);
                            dbUtils.setHostStatus(host.getId(), userLogin, HostStatus.FATAL);
                            HttpUtils.sendSyncRequest(new URL(HttpUtils.buildURLForSync(host, host.getSyncid(), HEARTBEAT_INTERVAL)));
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
    }
}