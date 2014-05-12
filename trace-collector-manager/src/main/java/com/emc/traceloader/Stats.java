package com.emc.traceloader;

import com.emc.traceloader.db.entity.Host;
import com.emc.traceloader.db.entity.HostStatus;
import com.emc.traceloader.db.entity.SystemStatus;

import java.util.Iterator;
import java.util.Map;

public class Stats {

    private SystemStatus systemStatus;
    private Map<HostStatus, Host> hostStatus;
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Map<HostStatus, Host> getHostStatus() {
        return hostStatus;
    }

    public void setHostStatus(Map<HostStatus, Host> hostStatus) {
        this.hostStatus = hostStatus;
    }

    public SystemStatus getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(SystemStatus systemStatus) {
        this.systemStatus = systemStatus;
    }

    private Integer getHostCountByStatus(HostStatus status) {
        Integer result = 0;
        Iterator it = this.hostStatus.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            if((HostStatus)(pairs.getKey()) == status) {
                result ++;
            }
        }
        return result;
    }

    public String buildSystemStats() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table><tbody>");
        sb.append("<tr>");
        sb.append("<td>System status:</td>");
        sb.append("<td>" + this.getSystemStatus() + "</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>Hosts available:</td>");
        sb.append("<td>" + this.getTotal() + "</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>Operational:</td>");
        sb.append("<td>" + this.getHostCountByStatus(HostStatus.OPERATIONAL) + "</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>Warning:</td>");
        sb.append("<td>" + this.getHostCountByStatus(HostStatus.WARNING) + "</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>Fatal:</td>");
        sb.append("<td>" + this.getHostCountByStatus(HostStatus.FATAL) + "</td>");
        sb.append("</tr>");
        sb.append("</tbody></table>");
        return sb.toString();
    }
}
