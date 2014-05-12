package com.emc.traceloader.auth;

import com.emc.traceloader.service.Services;
import com.emc.traceloader.service.db.DatabaseService;
import com.emc.traceloader.service.db.impl.entity.User;
import com.emc.traceloader.keeper.api.AuthInfo;
import com.emc.traceloader.service.keeper.KeeperService;
import com.emc.traceloader.session.SessionParameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(
        name = "TLUManageAuth",
        urlPatterns = {"/auth"}
)
public class ManagerAuthProcessor extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ManagerAuthProcessor.class.toString());

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String KEEPER_URL_PARAMETER = "keeper_url";
    private static final String LOGOUT_PARAMETER = "logout";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        KeeperService keeperService = Services.keeperServiceInstance(getServletContext());
        DatabaseService databaseService = Services.databaseUtilsInstance(getServletContext());
        if(request.getParameter(LOGIN_PARAMETER) != null && request.getParameter(PASSWORD_PARAMETER) != null &&
                request.getParameter(KEEPER_URL_PARAMETER) != null) {
            keeperService.setKeeperUrl(request.getParameter(KEEPER_URL_PARAMETER));
            AuthInfo authInfo = keeperService.requestAuthorization(request.getParameter(LOGIN_PARAMETER), request.getParameter(PASSWORD_PARAMETER));
            if(authInfo != null && authInfo.getStatus() == 0) {
                request.getSession().setAttribute(SessionParameters.SESSION_ID_PARAM, authInfo.getSession_id());
                request.getSession().setAttribute(SessionParameters.USER_ID_PARAM, request.getParameter(LOGIN_PARAMETER));
                request.getSession().setAttribute(SessionParameters.KEEPER_URL_PARAM, request.getParameter(KEEPER_URL_PARAMETER));
                databaseService.addUser(new User(request.getParameter(LOGIN_PARAMETER)));
            }
        } else if(request.getParameter(LOGOUT_PARAMETER) != null) {
            request.getSession().invalidate();
        }
        request.getRequestDispatcher("").forward(request, response); //process jsp page
    }
}
