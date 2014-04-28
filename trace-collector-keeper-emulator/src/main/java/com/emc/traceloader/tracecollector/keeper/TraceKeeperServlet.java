package com.emc.traceloader.tracecollector.keeper;

import com.emc.traceloader.entity.MsgEntity;
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
        name = "TLKeeper",
        urlPatterns = {"/keeper"}
)
public class TraceKeeperServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(TraceKeeperServlet.class.toString());
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("POST request received!");
        StringBuffer jb = new StringBuffer();
        String line = null;
        MsgEntity msg = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            msg = gson.fromJson(jb.toString(), MsgEntity.class);
            logger.info("Msg received:");
            logger.info(msg.toString());
        } catch (Exception e) {
            throw new IOException("Error parsing JSON request string");
        }
    }
}
