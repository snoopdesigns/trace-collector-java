package com.emc.traceloader.unit.api;

import java.util.Collection;

public class CmdEntity {

    private ControlCmdType cmd_type;
    private Integer log_entity_per_msg;
    private Integer send_interval;
    private String postback_ip;

    public CmdEntity() {
    }

    public ControlCmdType getCmd_type() {
        return cmd_type;
    }

    public void setCmd_type(ControlCmdType cmd_type) {
        this.cmd_type = cmd_type;
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

    @Override
    public String toString() {
        return "CmdEntity{" +
                "cmd_type='" + cmd_type + '\'' +
                ", log_entity_per_msg=" + log_entity_per_msg +
                ", send_interval=" + send_interval +
                ", postback_ip='" + postback_ip + '\'' +
                '}';
    }
}
