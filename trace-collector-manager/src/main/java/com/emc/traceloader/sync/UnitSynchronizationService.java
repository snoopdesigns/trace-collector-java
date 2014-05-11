package com.emc.traceloader.sync;

import com.emc.traceloader.db.entity.Host;

public interface UnitSynchronizationService {

    public boolean checkUnitAvailable(Host host);
    public String generateSyncId(Host host);
}
