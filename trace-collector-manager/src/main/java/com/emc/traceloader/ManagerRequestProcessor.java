package com.emc.traceloader;

import com.emc.traceloader.service.Services;
import com.emc.traceloader.service.db.DatabaseService;
import com.emc.traceloader.session.SessionParameters;
import com.emc.traceloader.service.keeper.KeeperService;
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

    private ManagerController controller = null;

    private static final String GROUP_PARAMETER = "group_name";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(controller == null) {
            KeeperService keeperService = Services.keeperServiceInstance(getServletContext());
            DatabaseService dbUtils = Services.databaseUtilsInstance(getServletContext());
            controller = new ManagerController(keeperService, dbUtils);
        }
        if(!request.getParameterMap().isEmpty()) {
            logger.info("Parsing user request...");
            CmdEntity cmd = controller.parseUIRequest(request, SessionParameters.keeperUrl(request.getSession()));
            controller.processCommand(cmd, SessionParameters.sessionId(request.getSession()),
                    request.getParameter(GROUP_PARAMETER), SessionParameters.userId(request.getSession()));
        }
        request.getRequestDispatcher("").forward(request, response); //process jsp page
    }
}
