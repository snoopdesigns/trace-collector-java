package com.emc.traceloader;

import com.emc.traceloader.auth.AuthController;
import com.emc.traceloader.db.DatabaseUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ManagerContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent e) {
        com.objectdb.Enhancer.enhance("com.emc.traceloader.db.entity.*");
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("$objectdb/db/manager.odb");
        DatabaseUtils dbUtils = new DatabaseUtils(emf);
        e.getServletContext().setAttribute(DatabaseUtils.class.getName(), dbUtils);
        AuthController authController = new AuthController();
        e.getServletContext().setAttribute(AuthController.class.getName(), authController);
    }

    public void contextDestroyed(ServletContextEvent e) {
        DatabaseUtils dbUtils =
                (DatabaseUtils)e.getServletContext().getAttribute(DatabaseUtils.class.getName());
        dbUtils.destroy();
    }
}
