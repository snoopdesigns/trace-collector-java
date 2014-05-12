package com.emc.traceloader.db;

import com.emc.traceloader.db.entity.Host;
import com.emc.traceloader.db.entity.HostStatus;
import com.emc.traceloader.db.entity.User;
import com.emc.traceloader.httputils.HttpUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
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

    public List<Host> getAllHosts(String userLogin) {
        List<Host> result = new ArrayList<Host>();
        try {
            Query query = em.createQuery("SELECT u FROM User u where u.login = :login", User.class);
            query.setParameter("login", userLogin);
            List<User> user  = query.getResultList();
            if(user.size() > 0) {
                result.addAll(user.get(0).getHosts());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addHost(Host host, String userLogin) {
        try {
            User user  = (User)em.find(User.class, this.getUserIdByLogin(userLogin));
            em.getTransaction().begin();
            user.addNewHost(host);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteHost(Long id, String userLogin) {
        try {
            User user  = (User)em.find(User.class, this.getUserIdByLogin(userLogin));
            em.getTransaction().begin();
            user.deleteHost(id);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHostSelected(Long id, String userLogin, boolean selected) {
        try {
            User user  = (User)em.find(User.class, this.getUserIdByLogin(userLogin));
            em.getTransaction().begin();
            user.setHostSelected(id, selected);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHostStatus(Long id, String userLogin, HostStatus status) {
        try {
            User user  = (User)em.find(User.class, this.getUserIdByLogin(userLogin));
            em.getTransaction().begin();
            user.setHostStatus(id, status);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<URL> getHostsURLsWithoutSelected(String userLogin) {
        List<URL> result = new ArrayList<URL>();
        try {
        for(Host host : this.getAllHosts(userLogin)) {
            if(host.isSelected()) {
                result.add(new URL(HttpUtils.buildURLForUnit(host)));
            }
        }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Long getUserIdByLogin(String userLogin) {
        Long result = null;
        try {
            Query query = em.createQuery("SELECT u.id FROM User u where u.login = :login", Long.class);
            query.setParameter("login", userLogin);
            List<Long> ids  = query.getResultList();
            if(ids.size() > 0) {
                result = ids.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
