package com.emc.traceloader.entity;

public class LogEntity {

    private String op_code;
    private String device_id;
    private String op_address;
    private String datetime;

    public LogEntity() {
    }

    public String getOp_code() {
        return op_code;
    }

    public void setOp_code(String op_code) {
        this.op_code = op_code;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getOp_address() {
        return op_address;
    }

    public void setOp_address(String op_address) {
        this.op_address = op_address;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "LogEntity{" +
                "op_code='" + op_code + '\'' +
                ", device_id='" + device_id + '\'' +
                ", op_address='" + op_address + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
