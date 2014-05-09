package com.emc.traceloader.httputils;

import com.emc.traceloader.db.entity.Host;
import com.emc.traceloader.unit.api.CmdEntity;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

public class HttpUtils {

    private static final Logger logger = Logger.getLogger(HttpUtils.class.toString());

    private static final String HTTP_PROTOCOL = "http://";
    private static final String COLON = ":";
    private static final String UNIT_URL_CONTEXT = "/unit";

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

    public static String buildURL(Host host) {
        StringBuilder sb = new StringBuilder();
        sb.append(HTTP_PROTOCOL);
        sb.append(host.getIp());
        sb.append(COLON);
        sb.append(host.getPort());
        sb.append(UNIT_URL_CONTEXT);
        return sb.toString();
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
