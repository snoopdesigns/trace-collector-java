package com.emc.traceloader.tracecollector.unit.synchronization;

import com.emc.traceloader.tracecollector.unit.synchronization.service.SynchronizationService;

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
    private static final String INTERVAL_PARAM = "interval";
    private static final String ACTION_PARAM = "action";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter(SYNC_ID_PARAM) != null && req.getParameter(ACTION_PARAM) != null) {
            if(req.getParameter(ACTION_PARAM).equals("start") && req.getParameter(INTERVAL_PARAM) != null) {
                ((SynchronizationService) getServletContext().getAttribute(SynchronizationService.class.toString())).startSynchronization(
                    req.getParameter(SYNC_ID_PARAM),Integer.valueOf(req.getParameter(INTERVAL_PARAM)),
                    new URL("http://"+req.getRemoteAddr()+":8080/mgr/sync?id="+req.getParameter(SYNC_ID_PARAM))
                );
            } else if(req.getParameter(ACTION_PARAM).equals("stop")) {
                ((SynchronizationService) getServletContext().getAttribute(SynchronizationService.class.toString())).stopSynchronization(
                        req.getParameter(SYNC_ID_PARAM)
                );
            }
        }
        resp.setStatus(200);
    }
}
