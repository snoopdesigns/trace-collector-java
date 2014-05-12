package com.emc.traceloader.service;

import com.emc.traceloader.service.db.DatabaseService;
import com.emc.traceloader.service.keeper.KeeperService;
import com.emc.traceloader.service.sync.UnitSynchronizationService;

import javax.servlet.ServletContext;

public class Services {

    public static DatabaseService databaseUtilsInstance(ServletContext c) {
        return (DatabaseService)c.getAttribute(DatabaseService.class.getName());
    }

    public static UnitSynchronizationService unitSynchronizationServiceInstance(ServletContext c) {
        return (UnitSynchronizationService)c.getAttribute(UnitSynchronizationService.class.getName());
    }

    public static KeeperService keeperServiceInstance(ServletContext c) {
        return (KeeperService)c.getAttribute(KeeperService.class.getName());
    }
}
