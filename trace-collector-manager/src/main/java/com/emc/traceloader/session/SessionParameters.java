package com.emc.traceloader.session;

import javax.servlet.http.HttpSession;

public class SessionParameters {

    public static final String SESSION_ID_PARAM = "TL_SESSION_ID";
    public static final String USER_ID_PARAM = "TL_USER_ID";
    public static final String KEEPER_URL_PARAM = "TL_KEEPER_URL";

    public static String sessionId(HttpSession s) {
        return (String)s.getAttribute(SESSION_ID_PARAM);
    }

    public static String userId(HttpSession s) {
        return (String)s.getAttribute(USER_ID_PARAM);
    }

    public static String keeperUrl(HttpSession s) {
        return (String)s.getAttribute(KEEPER_URL_PARAM);
    }
}
