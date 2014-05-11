package com.emc.traceloader.tracecollector.unit.synchronization;

import com.emc.traceloader.tracecollector.unit.synchronization.service.SynchronizationService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

@WebServlet(
        name = "TLUnitSync",
        urlPatterns = {"/unit/sync"}
)
public class TraceLoaderUnitSynchronizationServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(TraceLoaderUnitSynchronizationServlet.class.toString());

    private static final String SYNC_ID_PARAM = "id";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("SYNC request received!");
        if(req.getParameter(SYNC_ID_PARAM) != null) {
            ((SynchronizationService) getServletContext().getAttribute(SynchronizationService.class.toString())).startSynchronization(
                req.getParameter(SYNC_ID_PARAM),
                new URL("http://"+req.getRemoteAddr()+":8080/mgr/sync?id="+req.getParameter(SYNC_ID_PARAM))
            );
        }
        resp.setStatus(200);
    }
}
