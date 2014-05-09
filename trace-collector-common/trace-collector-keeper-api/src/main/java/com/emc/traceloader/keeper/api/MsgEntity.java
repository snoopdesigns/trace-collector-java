package com.emc.traceloader.keeper.api;

import java.util.Collection;

public class MsgEntity {

    private DataCmdType msg_type;
    private Integer data_total;
    private Collection<LogEntity> data;

    public MsgEntity() {
    }

    public DataCmdType getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(DataCmdType msg_type) {
        this.msg_type = msg_type;
    }

    public Integer getData_total() {
        return data_total;
    }

    public void setData_total(Integer data_total) {
        this.data_total = data_total;
    }

    public Collection<LogEntity> getData() {
        return data;
    }

    public void setData(Collection<LogEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MsgEntity{" +
                "msg_type='" + msg_type + '\'' +
                ", data_total=" + data_total +
                ", data=" + data +
                '}';
    }
}
