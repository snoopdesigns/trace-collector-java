package com.emc.traceloader;

import com.emc.traceloader.db.DatabaseUtils;
import com.emc.traceloader.httputils.HttpUtils;
import com.emc.traceloader.keeperservice.KeeperService;
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

    private KeeperService keeperService;
    private DatabaseUtils dbUtils;

    private static final String PARAM_CMD_TYPE = "cmd_type";
    private static final String PARAM_LOG_ENTITY_PER_MSG = "log_entity_per_msg";
    private static final String PARAM_SEND_INTERVAL = "send_interval";

    public ManagerController(KeeperService keeperService, DatabaseUtils dbUtils) {
        this.keeperService = keeperService;
        this.dbUtils = dbUtils;
    }

    public CmdEntity parseUIRequest(HttpServletRequest request, String postback_ip) {
        CmdEntity cmd = new CmdEntity();
        if(request.getParameter(PARAM_CMD_TYPE) != null &&
                request.getParameter(PARAM_LOG_ENTITY_PER_MSG) != null &&
                request.getParameter(PARAM_SEND_INTERVAL) != null) {
            cmd.setCmd_type(ControlCmdType.valueOf(request.getParameter(PARAM_CMD_TYPE)));
            cmd.setLog_entity_per_msg(Integer.valueOf(request.getParameter(PARAM_LOG_ENTITY_PER_MSG)));
            cmd.setSend_interval(Integer.valueOf(request.getParameter(PARAM_SEND_INTERVAL)));
            cmd.setPostback_ip(postback_ip);
        }
        return cmd;
    }

    public void processCommand(CmdEntity cmd, String sessionId, String groupName) {
        StringBuilder sb = new StringBuilder();
        sb.append("SID=");
        sb.append(sessionId);
        if(cmd.getCmd_type() == ControlCmdType.SEND) {
            String groupId = keeperService.requestGroupId(sessionId, groupName);
            sb.append("GID=");
            sb.append(groupId);
        }
        HttpUtils.sendCommand(cmd, dbUtils.getHostsURLsOnlySelected(sessionId), sb.toString());
    }
}
