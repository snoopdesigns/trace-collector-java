package com.emc.traceloader.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private List<Host> hosts;

    public User() {

    }

    public User(String login) {
        this.login = login;
        this.hosts = new ArrayList<Host>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public void addNewHost(Host host) {
        host.setId(new Long(hosts.size() + 1));
        this.hosts.add(host);
    }

    public void deleteHost(Long id) {
        for(int i=0;i<hosts.size();i++) {
            if(hosts.get(i).getId().compareTo(id) == 0) {
                this.hosts.remove(i);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", hosts=" + hosts +
                '}';
    }
}
