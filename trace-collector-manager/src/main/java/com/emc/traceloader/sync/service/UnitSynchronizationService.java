package com.emc.traceloader.sync.service;

import com.emc.traceloader.db.entity.Host;

public interface UnitSynchronizationService {
    public boolean checkUnitAvailable(Host host);
    public String generateSyncId(Host host);
    public void monitorSync(Host host, String userLogin);
    public void stopMonitoring(String syncid);
    public void destroy();
}
