package com.emc.traceloader.tracecollector.unit.synchronization.service.impl;

public class HostIdentity {

    private Integer userid;
    private Integer hostid;

    public HostIdentity(Integer userid, Integer hostid) {
        this.userid = userid;
        this.hostid = hostid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getHostid() {
        return hostid;
    }

    public void setHostid(Integer hostid) {
        this.hostid = hostid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HostIdentity that = (HostIdentity) o;

        if (!hostid.equals(that.hostid)) return false;
        if (!userid.equals(that.userid)) return false;

        return true;
    }

    @Override
    public String toString() {
        return "HostIdentity{" +
                "userid=" + userid +
                ", hostid=" + hostid +
                '}';
    }
}
