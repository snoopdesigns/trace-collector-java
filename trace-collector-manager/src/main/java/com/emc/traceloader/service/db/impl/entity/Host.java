package com.emc.traceloader.service.db.impl.entity;

import java.io.Serializable;

public class Host implements Serializable{

    private static final long serialVersionUID = 1L;
    private Long id;
    private String ip;
    private String port;
    private String syncid;
    private HostStatus status;
    private boolean selected;

    public Host() {
    }
    public Host(String ip, String port) {
        this.ip = ip;
        this.port = port;
        this.selected = false;
        this.status = HostStatus.OPERATIONAL;
    }

    public HostStatus getStatus() {
        return status;
    }

    public void setStatus(HostStatus status) {
        this.status = status;
    }

    public String getSyncid() {
        return syncid;
    }

    public void setSyncid(String syncid) {
        this.syncid = syncid;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Host{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", syncid='" + syncid + '\'' +
                ", selected=" + selected +
                '}';
    }
}
