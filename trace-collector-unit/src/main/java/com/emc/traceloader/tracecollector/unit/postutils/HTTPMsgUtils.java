package com.emc.traceloader.tracecollector.unit.postutils;

import com.emc.traceloader.entity.Constants;
import com.emc.traceloader.entity.LogEntity;
import com.emc.traceloader.entity.MsgEntity;

import java.util.Collection;
import java.util.List;

public class HTTPMsgUtils {

    public static MsgEntity createDataMessage(Collection<LogEntity> data, int beginIndex, int endIndex) {
        MsgEntity msg = new MsgEntity();
        msg.setMsg_type(Constants.DataCmdType.TRACES_SEND_DATA);
        msg.setData(((List)data).subList(beginIndex, endIndex));
        msg.setData_total(data.size());
        return msg;
    }

    public static MsgEntity createBeginMessage(Collection<LogEntity> data) {
        MsgEntity msg = new MsgEntity();
        msg.setMsg_type(Constants.DataCmdType.TRACES_SEND_BEGIN);
        msg.setData_total(data.size());
        return msg;
    }

    public static MsgEntity createEndMessage(Collection<LogEntity> data) {
        MsgEntity msg = new MsgEntity();
        msg.setMsg_type(Constants.DataCmdType.TRACES_SEND_END);
        msg.setData_total(data.size());
        return msg;
    }
}
