package com.emc.traceloader.service.db;

import com.emc.traceloader.httputils.Stats;
import com.emc.traceloader.service.db.impl.entity.Host;
import com.emc.traceloader.service.db.impl.entity.HostStatus;
import com.emc.traceloader.service.db.impl.entity.User;
import java.net.URL;
import java.util.List;

public interface DatabaseService {

    public void destroy();

    public List<Host> getAllHosts(String userLogin);

    public void addHost(Host host, String userLogin);

    public void deleteHost(Long id, String userLogin);

    public Host getHostById(Long id, String userLogin);

    public void setHostSelected(Long id, String userLogin, boolean selected);

    public void setHostStatus(Long id, String userLogin, HostStatus status);

    public void addUser(User user);

    public List<URL> getHostsURLsOnlySelected(String userLogin);

    public Stats buildStats(String userLogin);
}
