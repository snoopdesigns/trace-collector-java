package com.emc.traceloader.tracecollector.unit;

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
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("Context destroyed!"); //need to clean up
        ((CmdHandler)servletContextEvent.getServletContext().getAttribute(CmdHandler.class.toString())).destroy();
    }
}
