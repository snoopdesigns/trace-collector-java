package com.emc.traceloader.service.keeper.impl;

import com.emc.traceloader.httputils.HttpUtils;
import com.emc.traceloader.keeper.api.AuthInfo;
import com.emc.traceloader.service.keeper.KeeperService;
import org.apache.commons.codec.binary.Base64;
import java.net.URL;
import java.util.logging.Logger;

public class KeeperServiceImpl implements KeeperService{

    private static final Logger logger = Logger.getLogger(KeeperServiceImpl.class.getName());

    private String keeperURL;

    @Override
    public void setKeeperUrl(String url) {
        this.keeperURL = url;
    }

    @Override
    public AuthInfo requestAuthorization(String username, String password) {
        AuthInfo result = new AuthInfo();
        try {
            URL keeperAuthUrl = new URL(keeperURL + "/auth/login");
            logger.info("Sending auth request to keeper: " + keeperAuthUrl);
            String sessionId = HttpUtils.sendKeeperAuthRequest(keeperAuthUrl, this.encodeCredentials(username, password));
            result.setSession_id(sessionId);
            result.setStatus(0);
            logger.info("Received SID = " + sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String requestGroupId(String sesionId, String groupName) {
        String result = null;
        try {
            URL keeperGroupUrl = new URL(keeperURL + "/group");
            logger.info("Sending group request to keeper: " + keeperGroupUrl);
            result = HttpUtils.sendKeeperGroupRequest(keeperGroupUrl, groupName, sesionId);
            logger.info("Received GID = " + result);
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
