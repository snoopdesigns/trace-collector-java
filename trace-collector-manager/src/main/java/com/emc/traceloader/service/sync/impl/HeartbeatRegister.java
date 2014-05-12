package com.emc.traceloader.service.sync.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class HeartbeatRegister {

    private static final Logger logger = Logger.getLogger(HeartbeatRegister.class.getName());

    private static final HeartbeatRegister INSTANCE = new HeartbeatRegister();
    private static Map<String, HeartbeatInstance> heartbeatsList;
    static {
        heartbeatsList = new HashMap<String, HeartbeatInstance>();
    }
    private HeartbeatRegister() {
    }

    public static HeartbeatRegister getInstance() {
        return INSTANCE;
    }

    public void newHeartbeatReceived(String syncid) {
        if(!heartbeatsList.containsKey(syncid)) {
            HeartbeatInstance hrtInstance = new HeartbeatInstance();
            heartbeatsList.put(syncid, hrtInstance);
        }
        logger.info("Registering new heartbeat for syncid = " + syncid);
        heartbeatsList.get(syncid).newHeartbeatReceived();
    }

    public Date getDateOfLastHeartbeat(String syncid) {
        return heartbeatsList.get(syncid).getDateOfLastHeartbeat();
    }

    private static class HeartbeatInstance {
        private final AtomicReference<Date> dateOfLastHeartbeat;

        private HeartbeatInstance() {
            dateOfLastHeartbeat = new AtomicReference<Date>();
        }

        public void newHeartbeatReceived() {
            this.dateOfLastHeartbeat.set(new Date());
        }

        public Date getDateOfLastHeartbeat() {
            return dateOfLastHeartbeat.get();
        }
    }
}
