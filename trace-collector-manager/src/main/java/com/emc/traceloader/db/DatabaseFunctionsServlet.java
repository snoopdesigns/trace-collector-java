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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DatabaseUtils dbUtils =
                (DatabaseUtils)getServletContext().getAttribute(DatabaseUtils.class.getName());
        dbUtils.deleteHost(Long.valueOf(request.getParameter("id")), (String)request.getSession().getAttribute(SessionParameters.USER_ID_PARAM));
    }
}

