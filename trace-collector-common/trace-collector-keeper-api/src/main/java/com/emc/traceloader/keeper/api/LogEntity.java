package com.emc.traceloader.keeper.api;

public class LogEntity {

    private String device_id;
    private String cdb_string;
    private String datetime;

    public LogEntity() {
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getCdb_string() {
        return cdb_string;
    }

    public void setCdb_string(String cdb_string) {
        this.cdb_string = cdb_string;
    }

    @Override
    public String toString() {
        return "LogEntity{" +
                "device_id='" + device_id + '\'' +
                ", cdb_string='" + cdb_string + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }

}
