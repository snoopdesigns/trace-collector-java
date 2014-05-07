package com.emc.traceloader.auth;

import com.emc.traceloader.db.DatabaseUtils;
import com.emc.traceloader.db.entity.User;
import com.emc.traceloader.keeper.api.AuthInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

@WebServlet(
        name = "TLUManageAuth",
        urlPatterns = {"/auth"}
)
public class ManagerAuthProcessor extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ManagerAuthProcessor.class.toString());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthController authController = (AuthController)getServletContext().getAttribute(AuthController.class.getName());
        DatabaseUtils databaseUtils = (DatabaseUtils)getServletContext().getAttribute(DatabaseUtils.class.getName());
        if(request.getParameter("login") != null && request.getParameter("password") != null &&
                request.getParameter("keeper_url") != null) {
            AuthInfo authInfo = authController.requestAuthorization(request.getParameter("keeper_url"),
                    request.getParameter("login"), request.getParameter("password"));

            if(authInfo != null && authInfo.getStatus() == 0) {
                request.getSession().setAttribute(SessionParameters.SESSION_ID_PARAM, authInfo.getSession_id());
                request.getSession().setAttribute(SessionParameters.USER_ID_PARAM, request.getParameter("login"));
                request.getSession().setAttribute(SessionParameters.KEEPER_URL_PARAM, request.getParameter("keeper_url"));
                databaseUtils.addUser(new User(request.getParameter("login")));
            }
        } else if(request.getParameter("logout") != null) {
            request.getSession().invalidate();
        }
        request.getRequestDispatcher("").forward(request, response); //process jsp page
    }
}
