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
        name = "TLKeeperAuth",
        urlPatterns = {"/keeper/auth/login"}
)
public class TraceKeeperAuthServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(TraceKeeperAuthServlet.class.toString());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Auth request received...");
        String authorizationString = request.getHeader("Authorization");
        String encodedCredentials = authorizationString.split(" ")[1];
        String decodedCredentials = this.decodeCredentials(encodedCredentials);
        logger.info("Username: " + decodedCredentials.split(":")[0] + ", password: " + decodedCredentials.split(":")[1]);
        response.setHeader("Set-Cookie", "SID=12345");
        response.setStatus(200);

    }

    private String decodeCredentials(String encodedCredentials) {
        byte[] decodedBytes = Base64.decodeBase64(encodedCredentials.getBytes());
        return new String(decodedBytes);
    }
}
