package com.emc.traceloader.tracecollector.unit.synchronization.service;

import java.net.URL;

public interface SynchronizationService {

    public void startSynchronization(String syncid, Integer heartbeatInterval, URL managerUrl);
    public void stopSynchronization(String syncid);
}
