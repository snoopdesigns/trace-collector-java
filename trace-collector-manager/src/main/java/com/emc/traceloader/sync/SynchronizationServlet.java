package com.emc.traceloader.sync;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(
        name = "TLUManagerSync",
        urlPatterns = {"/mgr/sync"}
)
public class SynchronizationServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SynchronizationServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UnitSynchronizationService syncUtils =
                (UnitSynchronizationService)getServletContext().getAttribute(UnitSynchronizationService.class.getName());
        logger.info("Received heartbeat from: " + request.getParameter("id"));
    }
}
