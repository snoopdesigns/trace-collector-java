package com.emc.traceloader.tracecollector.unit.postutils;

import com.emc.traceloader.entity.LogEntity;
import com.emc.traceloader.entity.MsgEntity;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import java.net.URL;
import java.util.Collection;
import java.util.logging.Logger;

public class PostTask implements Runnable{

    private static final Logger logger = Logger.getLogger(PostTask.class.toString());

    private Gson gson = new Gson();

    private URL postbackURL;
    private Integer logsPerMsg;
    private Collection<LogEntity> data;
    private Integer sendInterval; // interval in milliseconds

    public PostTask(URL postbackURL, Integer logsPerMsg, Collection<LogEntity> data, Integer sendInterval) {
        this.postbackURL = postbackURL;
        this.logsPerMsg = logsPerMsg;
        this.data = data;
        this.sendInterval = sendInterval;
    }

    @Override
    public void run(){
        logger.info("Starting traces transmission. Postback URL: " + postbackURL + ", send interval: " + sendInterval);
        int currentOffset = 0;
        while(true) {
            try {
                if(currentOffset == 0) {
                    logger.info("Sending TRACES_SEND_BEGIN message...");
                    this.sendPostMessage(HTTPMsgUtils.createBeginMessage(data));
                } else if(currentOffset > 0 && currentOffset <= data.size()) {
                    logger.info("Sending TRACES_SEND_DATA message... Offset = " + currentOffset);
                    if(currentOffset + logsPerMsg > data.size()) {
                        logger.info("Sending data message...");
                        this.sendPostMessage(HTTPMsgUtils.createDataMessage(data, currentOffset, data.size()));
                    } else {
                        this.sendPostMessage(HTTPMsgUtils.createDataMessage(data, currentOffset, currentOffset + logsPerMsg));
                    }
                    currentOffset += logsPerMsg;
                } else if(currentOffset > data.size()) {
                    logger.info("Sending TRACES_SEND_END message...");
                    this.sendPostMessage(HTTPMsgUtils.createEndMessage(data));
                    break;//end of sending data
                }
                Thread.sleep(sendInterval);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendPostMessage(MsgEntity msg) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(postbackURL.toURI());
            post.setEntity(new StringEntity(gson.toJson(msg)));
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
                public String getName() {return "Accept";}
                @Override
                public String getValue() {return "application/json";}
                @Override
                public HeaderElement[] getElements() throws ParseException {return new HeaderElement[0];}
            });
            client.execute(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
