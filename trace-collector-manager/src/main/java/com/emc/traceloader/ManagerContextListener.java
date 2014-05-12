package com.emc.traceloader;

import com.emc.traceloader.service.db.DatabaseService;
import com.emc.traceloader.service.db.impl.DatabaseServiceImpl;
import com.emc.traceloader.service.keeper.KeeperService;
import com.emc.traceloader.service.keeper.impl.KeeperServiceImpl;
import com.emc.traceloader.service.sync.UnitSynchronizationService;
import com.emc.traceloader.service.sync.impl.UnitSynchronizationServiceImpl;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ManagerContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent e) {
        com.objectdb.Enhancer.enhance("com.emc.traceloader.service.db.impl.entity.User");
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("$objectdb/db/manager.odb");
        DatabaseService dbUtils = new DatabaseServiceImpl(emf);
        e.getServletContext().setAttribute(DatabaseService.class.getName(), dbUtils);
        UnitSynchronizationService unitSynchronizationService = new UnitSynchronizationServiceImpl(dbUtils);
        e.getServletContext().setAttribute(UnitSynchronizationService.class.getName(), unitSynchronizationService);
        KeeperService keeperService = new KeeperServiceImpl();
        e.getServletContext().setAttribute(KeeperService.class.getName(), keeperService);
    }

    public void contextDestroyed(ServletContextEvent e) {
        DatabaseService dbUtils =
                (DatabaseService)e.getServletContext().getAttribute(DatabaseService.class.getName());
        dbUtils.destroy();
        UnitSynchronizationService unitSynchronizationService =
                (UnitSynchronizationService)e.getServletContext().getAttribute(UnitSynchronizationService.class.getName());
        unitSynchronizationService.destroy();
    }
}
