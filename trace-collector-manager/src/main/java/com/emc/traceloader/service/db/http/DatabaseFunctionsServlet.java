package com.emc.traceloader.service.db.http;

import com.emc.traceloader.service.Services;
import com.emc.traceloader.service.db.DatabaseService;
import com.emc.traceloader.session.SessionParameters;
import com.emc.traceloader.service.db.impl.entity.Host;
import com.emc.traceloader.service.sync.UnitSynchronizationService;
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

    private static final String ACTION_PARAMETER = "action";
    private static final String SELECTED_PARAMETER = "selected";
    private static final String ID_PARAMETER = "id";

    private static final String DELETE_ACTION = "delete";
    private static final String SELECT_ACTION = "select";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseService dbUtils = Services.databaseUtilsInstance(getServletContext());
        UnitSynchronizationService unitSynchronizationService = Services.unitSynchronizationServiceInstance(getServletContext());
        String userId = SessionParameters.userId(request.getSession());
        if(request.getParameter(ACTION_PARAMETER) != null && request.getParameter(ID_PARAMETER) != null) {
            if(request.getParameter(ACTION_PARAMETER).equals(DELETE_ACTION)) {
                Host host = dbUtils.getHostById(Long.valueOf(request.getParameter(ID_PARAMETER)), userId);
                unitSynchronizationService.stopMonitoring(host);
                dbUtils.deleteHost(Long.valueOf(request.getParameter(ID_PARAMETER)), userId);
            } else if(request.getParameter(ACTION_PARAMETER).equals(SELECT_ACTION)) {
                dbUtils.setHostSelected(Long.valueOf(request.getParameter(ID_PARAMETER)), userId,
                        Boolean.parseBoolean(request.getParameter(SELECTED_PARAMETER)));
            }
        }
    }
}

