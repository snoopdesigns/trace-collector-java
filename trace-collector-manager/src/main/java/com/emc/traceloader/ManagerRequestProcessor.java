package com.emc.traceloader;

import com.emc.traceloader.db.DatabaseUtils;
import com.emc.traceloader.unit.api.CmdEntity;
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
        name = "TLUManager",
        urlPatterns = {"/mgr"}
)
public class ManagerRequestProcessor extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ManagerController.class.toString());
    private Gson gson = new Gson();
    private ManagerController controller = new ManagerController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DatabaseUtils dbUtils =
                (DatabaseUtils)getServletContext().getAttribute(DatabaseUtils.class.getName());
        logger.info("Execute select: " + dbUtils.getAllHosts());

        logger.info("GET request received! PATH:" + request.getContextPath());
        if(!request.getParameterMap().isEmpty()) {
            logger.info("Parsing user request..." + request.getQueryString() + ", " + request.getContextPath());
            CmdEntity cmd = controller.parseUIRequest(request);
            logger.info("Sending command: " + cmd + " to URL: " + request.getParameter("send_address"));
            controller.sendCommand(cmd, new URL(request.getParameter("send_address")));
        }
        logger.info("Processing index.jsp...");
        request.getRequestDispatcher("").forward(request, response); //process jsp page
    }
}
