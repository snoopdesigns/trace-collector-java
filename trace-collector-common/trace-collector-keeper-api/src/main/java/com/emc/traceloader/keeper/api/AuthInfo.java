package com.emc.traceloader.keeper.api;

public class AuthInfo {

    private Integer status;
    private String session_id;

    public AuthInfo() {
    }

    public AuthInfo(String session_id) {
        this.session_id = session_id;
        this.status = 0;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    @Override
    public String toString() {
        return "AuthInfo{" +
                "status=" + status +
                ", session_id='" + session_id + '\'' +
                '}';
    }
}
