package com.emc.traceloader.tracecollector.unit;

import com.emc.traceloader.tracecollector.unit.synchronization.service.SynchronizationService;
import com.emc.traceloader.tracecollector.unit.synchronization.service.impl.SynchronizationServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

public class TraceLoaderContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(TraceLoaderUnitServlet.class.toString());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("Context initialized!");
        ServletContext sc = servletContextEvent.getServletContext();
        sc.setAttribute(CmdHandler.class.toString(), new CmdHandler());
        sc.setAttribute(SynchronizationService.class.toString(), new SynchronizationServiceImpl());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("Context destroyed!"); //need to clean up
        ((CmdHandler)servletContextEvent.getServletContext().getAttribute(CmdHandler.class.toString())).destroy();
    }
}
