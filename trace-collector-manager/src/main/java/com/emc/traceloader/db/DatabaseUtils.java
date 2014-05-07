package com.emc.traceloader.db;

import com.emc.traceloader.db.entity.Host;
import com.emc.traceloader.httputils.HttpUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    private EntityManagerFactory emf;
    private EntityManager em;

    public DatabaseUtils(EntityManagerFactory emf) {
        this.emf = emf;
        this.em = emf.createEntityManager();
    }

    public void destroy() {
        emf.close();
    }

    public List<Host> getAllHosts() {
        List<Host> result = new ArrayList<Host>();
        try {
            result = em.createQuery("SELECT g FROM Host g", Host.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addHost(Host host) {
        try {
            em.getTransaction().begin();
            em.persist(host);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<URL> getHostsURLs() {
        List<URL> result = new ArrayList<URL>();
        try {
        for(Host host : this.getAllHosts()) {
            result.add(new URL(HttpUtils.buildURL(host)));
        }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }


}
