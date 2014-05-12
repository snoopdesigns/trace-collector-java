package com.emc.traceloader.tracecollector.keeper;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

@WebServlet(
        name = "TLKeeperGroup",
        urlPatterns = {"/keeper/group"}
)
public class TraceKeeperGroupServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(TraceKeeperAuthServlet.class.toString());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Group create request received...");
        String authorizationString = request.getHeader("Cookie");
        String groupParameter = authorizationString.split(";")[0];
        String sessionParameter = authorizationString.split(";")[1];
        groupParameter = groupParameter.split("=")[1];
        sessionParameter = sessionParameter.split("=")[1];
        logger.info("Requested group: " + groupParameter + ", SID: " + sessionParameter);
        response.setHeader("Set-Cookie", "GID=0001");
        response.setStatus(200);

    }
}
