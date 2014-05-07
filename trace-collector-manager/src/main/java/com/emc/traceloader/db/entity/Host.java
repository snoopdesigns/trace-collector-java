package com.emc.traceloader.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

public class Host implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String ip;
    private String port;
    private String urlContext;

    public Host() {
    }

    public Host(String ip, String port, String urlContext) {
        this.ip = ip;
        this.port = port;
        this.urlContext = urlContext;
    }

    public String getUrlContext() {
        return urlContext;
    }

    public void setUrlContext(String urlContext) {
        this.urlContext = urlContext;
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
                '}';
    }
}
