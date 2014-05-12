package com.emc.traceloader;

import com.emc.traceloader.auth.SessionParameters;
import com.emc.traceloader.db.DatabaseUtils;
import com.emc.traceloader.keeperservice.KeeperService;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(controller == null) {
            KeeperService keeperService = (KeeperService)getServletContext().getAttribute(KeeperService.class.getName());
            DatabaseUtils dbUtils = (DatabaseUtils)getServletContext().getAttribute(DatabaseUtils.class.getName());
            controller = new ManagerController(keeperService, dbUtils);
        }
        if(!request.getParameterMap().isEmpty()) {
            logger.info("Parsing user request...");
            CmdEntity cmd = controller.parseUIRequest(request, (String)request.getSession().getAttribute(SessionParameters.KEEPER_URL_PARAM));
            controller.processCommand(cmd, (String)request.getSession().getAttribute(SessionParameters.SESSION_ID_PARAM),
                    request.getParameter("group_name"));
        }
        request.getRequestDispatcher("").forward(request, response); //process jsp page
    }
}
