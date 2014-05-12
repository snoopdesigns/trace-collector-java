package com.emc.traceloader.sync;

import com.emc.traceloader.sync.service.impl.HeartbeatRegister;

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
        if(request.getParameter("id") != null) {
            HeartbeatRegister.getInstance().newHeartbeatReceived(request.getParameter("id"));
        } else {
            logger.info("Received heartbeat from unknown syncid!");
        }
    }
}
