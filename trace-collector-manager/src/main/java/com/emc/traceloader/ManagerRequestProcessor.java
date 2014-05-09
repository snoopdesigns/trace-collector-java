package com.emc.traceloader;

import com.emc.traceloader.auth.SessionParameters;
import com.emc.traceloader.db.DatabaseUtils;
import com.emc.traceloader.unit.api.CmdEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(
        name = "TLUManager",
        urlPatterns = {"/mgr"}
)
public class ManagerRequestProcessor extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ManagerRequestProcessor.class.toString());
    private ManagerController controller = new ManagerController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DatabaseUtils dbUtils =
                (DatabaseUtils)getServletContext().getAttribute(DatabaseUtils.class.getName());
        logger.info("Execute select: " + dbUtils.getAllHosts((String)request.getSession().getAttribute(SessionParameters.USER_ID_PARAM)));

        logger.info("GET request received! PATH:" + request.getContextPath());
        if(!request.getParameterMap().isEmpty()) {
            logger.info("Parsing user request...");
            CmdEntity cmd = controller.parseUIRequest(request);
            controller.sendCommand(cmd, dbUtils.getHostsURLsWithoutSelected((String) request.getSession().getAttribute(SessionParameters.USER_ID_PARAM)));
        }
        request.getRequestDispatcher("").forward(request, response); //process jsp page
    }
}
