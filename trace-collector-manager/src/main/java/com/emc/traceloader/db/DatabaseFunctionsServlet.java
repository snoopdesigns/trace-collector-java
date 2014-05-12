package com.emc.traceloader.db;

import com.emc.traceloader.auth.SessionParameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(
        name = "TLUManagerDB",
        urlPatterns = {"/db"}
)
public class DatabaseFunctionsServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(DatabaseFunctionsServlet.class.toString());

    private static final String DELTE_ACTION = "delete";
    private static final String SELECT_FUNCTION = "select";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DatabaseUtils dbUtils =
                (DatabaseUtils)getServletContext().getAttribute(DatabaseUtils.class.getName());
        if(request.getParameter("action") != null) {
            if(request.getParameter("action").equals(DELTE_ACTION)) {
                dbUtils.deleteHost(Long.valueOf(request.getParameter("id")),
                        (String)request.getSession().getAttribute(SessionParameters.USER_ID_PARAM));
            } else if(request.getParameter("action").equals(SELECT_FUNCTION)) {
                dbUtils.setHostSelected(Long.valueOf(request.getParameter("id")),
                        (String)request.getSession().getAttribute(SessionParameters.USER_ID_PARAM),
                        Boolean.parseBoolean(request.getParameter("selected")));
            }
        }
    }
}
