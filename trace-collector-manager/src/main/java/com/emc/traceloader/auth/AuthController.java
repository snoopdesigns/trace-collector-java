package com.emc.traceloader.auth;

import com.emc.traceloader.httputils.HttpUtils;
import com.emc.traceloader.keeper.api.AuthInfo;
import org.apache.commons.codec.binary.Base64;

import java.net.URL;
import java.util.logging.Logger;

public class AuthController {

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    /* mock for keeper authorization mechanism */
    public AuthInfo requestAuthorization(String keeperURL, String username, String password) {
        AuthInfo result = new AuthInfo();
        try {
            URL keeperAuthUrl = new URL(keeperURL + "/auth/login");
            logger.info("Sending auth request to keeper: " + keeperAuthUrl);
            String sessionId = HttpUtils.sendKeeperAuthRequest(keeperAuthUrl, this.encodeCredentials(username, password)).split("=")[1];
            result.setSession_id(sessionId);
            result.setStatus(0);
            logger.info("Received SID = " + sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String encodeCredentials(String username, String password) {
        String toEncode = username+":"+password;
        byte[] encodedBytes = Base64.encodeBase64(toEncode.getBytes());
        return new String(encodedBytes);
    }
}
