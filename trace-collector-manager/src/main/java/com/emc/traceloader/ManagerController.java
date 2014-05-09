package com.emc.traceloader;

import com.emc.traceloader.httputils.HttpUtils;
import com.emc.traceloader.unit.api.CmdEntity;
import com.emc.traceloader.unit.api.ControlCmdType;
import com.emc.traceloader.unit.api.Politics;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ManagerController {

    private static Logger logger = Logger.getLogger(ManagerController.class.toString());

    private static final String PARAM_CMD_TYPE = "cmd_type";
    private static final String PARAM_SEND_POLITIC = "send_politic";
    private static final String PARAM_LOG_ENTITY_PER_MSG = "log_entity_per_msg";
    private static final String PARAM_SEND_INTERVAL = "send_interval";
    private static final String PARAM_POSTBACK_IP = "postback_ip";
    private static final String PARAM_REQUESTED_LUNS = "requested_luns"; //comma separated data

    public CmdEntity parseUIRequest(HttpServletRequest request) {
        CmdEntity cmd = new CmdEntity();
        if(request.getParameter(PARAM_CMD_TYPE) != null &&
                request.getParameter(PARAM_LOG_ENTITY_PER_MSG) != null &&
                request.getParameter(PARAM_POSTBACK_IP) != null &&
                request.getParameter(PARAM_REQUESTED_LUNS) != null &&
                request.getParameter(PARAM_SEND_INTERVAL) != null &&
                request.getParameter(PARAM_SEND_POLITIC) != null) {
            cmd.setCmd_type(ControlCmdType.valueOf(request.getParameter(PARAM_CMD_TYPE)));
            cmd.setSend_politic(Politics.valueOf(request.getParameter(PARAM_SEND_POLITIC)));
            cmd.setLog_entity_per_msg(Integer.valueOf(request.getParameter(PARAM_LOG_ENTITY_PER_MSG)));
            cmd.setSend_interval(Integer.valueOf(request.getParameter(PARAM_SEND_INTERVAL)));
            cmd.setPostback_ip(request.getParameter(PARAM_POSTBACK_IP));
            List<String> luns = Arrays.asList(request.getParameter(PARAM_REQUESTED_LUNS).split(","));
            cmd.setRequested_luns(luns);
        }
        return cmd;
    }

    public void sendCommand(CmdEntity cmd, List<URL> urls) {
        HttpUtils.sendCommand(cmd, urls);
    }
}
