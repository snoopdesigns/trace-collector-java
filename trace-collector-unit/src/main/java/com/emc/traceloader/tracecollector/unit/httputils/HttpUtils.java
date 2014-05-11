package com.emc.traceloader.tracecollector.unit.httputils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URL;
import java.util.logging.Logger;

public class HttpUtils {

    private static final Logger logger = Logger.getLogger(HttpUtils.class.toString());

    public static void sendHeartbeat(String syncid, URL managerUrl) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(managerUrl.toURI());
            logger.info("Sending heartbeat to URL: " + managerUrl.toString() + ", syncid = " + syncid);
            client.execute(get);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
