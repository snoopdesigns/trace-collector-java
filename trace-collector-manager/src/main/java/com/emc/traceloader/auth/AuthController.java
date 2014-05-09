package com.emc.traceloader.auth;

import com.emc.traceloader.keeper.api.AuthInfo;

import java.net.URL;

public class AuthController {


    /* mock for keeper authorization mechanism */
    public AuthInfo requestAuthorization(String keeperURL, String username, String password) {
        return new AuthInfo("12345", "Elizaveta");
    }
}
