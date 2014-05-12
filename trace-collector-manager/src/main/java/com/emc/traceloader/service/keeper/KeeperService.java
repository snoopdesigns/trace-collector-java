package com.emc.traceloader.service.keeper;

import com.emc.traceloader.keeper.api.AuthInfo;

public interface KeeperService {

    public void setKeeperUrl(String url);
    public AuthInfo requestAuthorization(String username, String password);
    public String requestGroupId(String sesionId, String groupName);
}
