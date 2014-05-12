package com.emc.traceloader.service.sync;

import com.emc.traceloader.service.db.impl.entity.Host;

public interface UnitSynchronizationService {
    public boolean checkUnitAvailable(Host host);
    public String generateSyncId(Host host);
    public void monitorSync(Host host, String userLogin);
    public void stopMonitoring(Host host);
    public void destroy();
}
