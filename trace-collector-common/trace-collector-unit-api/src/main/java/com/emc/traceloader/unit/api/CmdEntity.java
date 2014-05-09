package com.emc.traceloader.unit.api;

import java.util.Collection;

public class CmdEntity {

    private ControlCmdType cmd_type;
    private Politics send_politic;
    private Integer log_entity_per_msg;
    private Integer send_interval;
    private String postback_ip;
    private Collection<String> requested_luns;

    public CmdEntity() {
    }

    public ControlCmdType getCmd_type() {
        return cmd_type;
    }

    public void setCmd_type(ControlCmdType cmd_type) {
        this.cmd_type = cmd_type;
    }

    public Politics getSend_politic() {
        return send_politic;
    }

    public void setSend_politic(Politics send_politic) {
        this.send_politic = send_politic;
    }

    public Integer getLog_entity_per_msg() {
        return log_entity_per_msg;
    }

    public void setLog_entity_per_msg(Integer log_entity_per_msg) {
        this.log_entity_per_msg = log_entity_per_msg;
    }

    public Integer getSend_interval() {
        return send_interval;
    }

    public void setSend_interval(Integer send_interval) {
        this.send_interval = send_interval;
    }

    public String getPostback_ip() {
        return postback_ip;
    }

    public void setPostback_ip(String postback_ip) {
        this.postback_ip = postback_ip;
    }

    public Collection<String> getRequested_luns() {
        return requested_luns;
    }

    public void setRequested_luns(Collection<String> requested_luns) {
        this.requested_luns = requested_luns;
    }

    @Override
    public String toString() {
        return "CmdEntity{" +
                "cmd_type='" + cmd_type + '\'' +
                ", send_politic='" + send_politic + '\'' +
                ", log_entity_per_msg=" + log_entity_per_msg +
                ", send_interval=" + send_interval +
                ", postback_ip='" + postback_ip + '\'' +
                ", requested_luns=" + requested_luns +
                '}';
    }
}
