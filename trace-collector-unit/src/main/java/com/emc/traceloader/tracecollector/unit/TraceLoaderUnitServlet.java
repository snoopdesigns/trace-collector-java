package com.emc.traceloader.tracecollector.unit;

import com.emc.traceloader.unit.api.CmdEntity;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(
        name = "TLUnitCmd",
        urlPatterns = {"/unit"}
)
public class TraceLoaderUnitServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(TraceLoaderUnitServlet.class.toString());
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("GET request received!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("POST request received!");
        StringBuffer jb = new StringBuffer();
        String line = null;
        CmdEntity msg = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            msg = gson.fromJson(jb.toString(), CmdEntity.class);
            logger.info("Msg received:");
            logger.info(msg.toString());
        } catch (Exception e) {
            throw new IOException("Error parsing JSON request string");
        }
        ((CmdHandler) getServletContext().getAttribute(CmdHandler.class.toString())).handleExternalCommand(msg);
    }
}
