package com.emc.traceloader.service.sync.http;

import com.emc.traceloader.service.sync.impl.HeartbeatRegister;

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

    private static final String ID_PARAMETER = "id";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter(ID_PARAMETER) != null) {
            HeartbeatRegister.getInstance().newHeartbeatReceived(request.getParameter(ID_PARAMETER));
        } else {
            logger.info("Received heartbeat from unknown syncid!");
        }
    }
}
