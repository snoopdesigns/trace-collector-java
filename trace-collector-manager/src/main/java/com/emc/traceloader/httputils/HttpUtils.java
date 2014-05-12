package com.emc.traceloader.httputils;

import com.emc.traceloader.db.entity.Host;
import com.emc.traceloader.unit.api.CmdEntity;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

public class HttpUtils {

    private static final Logger logger = Logger.getLogger(HttpUtils.class.toString());

    private static final String HTTP_PROTOCOL = "http://";
    private static final String COLON = ":";
    private static final String Q = "?";
    private static final String AMPERSAND = "&";
    private static final String SYNC_ID_PARAM = "id";
    private static final String INTERVAL_PARAM = "interval";
    private static final String UNIT_URL_CONTEXT = "/unit";
    private static final String SYNC_URL_CONTEXT = "/unit/sync";

    private static Gson gson = new Gson();

    public static void sendCommand(CmdEntity cmd, List<URL> urls) {
        try {
            HttpClient client = getThreadSafeClient();
            for(URL url : urls) {
                HttpPost post = new HttpPost(url.toURI());
                post.setEntity(new StringEntity(gson.toJson(cmd)));
                post.setHeader(new Header() {
                    @Override
                    public String getName() {return "Content-type";}
                    @Override
                    public String getValue() {return "application/json";}
                    @Override
                    public HeaderElement[] getElements() throws ParseException {return new HeaderElement[0];}
                });
                post.setHeader(new Header() {
                    @Override
                    public String getName() {
                        return "Accept";
                    }

                    @Override
                    public String getValue() {
                        return "application/json";
                    }

                    @Override
                    public HeaderElement[] getElements() throws ParseException {
                        return new HeaderElement[0];
                    }
                });
                logger.info("Sending command: " + cmd + " to URL: " + url.toString());
                client.execute(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String buildURLForUnit(Host host) {
        StringBuilder sb = new StringBuilder();
        sb.append(HTTP_PROTOCOL);
        sb.append(host.getIp());
        sb.append(COLON);
        sb.append(host.getPort());
        sb.append(UNIT_URL_CONTEXT);
        return sb.toString();
    }

    public static String buildURLForSync(Host host, String syncid, Integer heartbeatInterval) {
        StringBuilder sb = new StringBuilder();
        sb.append(HTTP_PROTOCOL);
        sb.append(host.getIp());
        sb.append(COLON);
        sb.append(host.getPort());
        sb.append(SYNC_URL_CONTEXT);
        if(syncid != null) {
            sb.append(Q);
            sb.append(SYNC_ID_PARAM);
            sb.append("=");
            sb.append(syncid);
        }
        if(heartbeatInterval != null) {
            sb.append(AMPERSAND);
            sb.append(INTERVAL_PARAM);
            sb.append("=");
            sb.append(heartbeatInterval);
        }
        return sb.toString();
    }

    public static boolean checkURLAvailable(URL url) {
        boolean result = false;
        try {
            HttpClient client = getThreadSafeClient();
            HttpGet get = new HttpGet(url.toURI());
            logger.info("Checking address available:" + url);
            HttpResponse response = client.execute(get);
            if(response.getStatusLine().getStatusCode() == 200) {
                result = true;
            }
        } catch (Exception e) {
            result = false;
            logger.info("Host is unavailable!");
        }
        return result;
    }

    public static void sendSyncRequest(URL url) {
        try {
            HttpClient client = getThreadSafeClient();
            HttpGet get = new HttpGet(url.toURI());
            HttpResponse response = client.execute(get);
        } catch (Exception e) {
            logger.info("Host is unavailable!");
        }
    }

    private static DefaultHttpClient getThreadSafeClient()  {
        DefaultHttpClient client = new DefaultHttpClient();
        ClientConnectionManager mgr = client.getConnectionManager();
        HttpParams params = client.getParams();
        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,
            mgr.getSchemeRegistry()), params);
        return client;
    }
}
