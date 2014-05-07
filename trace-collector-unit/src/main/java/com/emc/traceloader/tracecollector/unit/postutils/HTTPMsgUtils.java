package com.emc.traceloader.tracecollector.unit.postutils;

import com.emc.traceloader.keeper.api.DataCmdType;
import com.emc.traceloader.keeper.api.LogEntity;
import com.emc.traceloader.keeper.api.MsgEntity;

import java.util.Collection;
import java.util.List;

public class HTTPMsgUtils {

    public static MsgEntity createDataMessage(Collection<LogEntity> data, int beginIndex, int endIndex) {
        MsgEntity msg = new MsgEntity();
        msg.setMsg_type(DataCmdType.TRACES_SEND_DATA);
        msg.setData(((List)data).subList(beginIndex, endIndex));
        msg.setData_total(data.size());
        return msg;
    }

    public static MsgEntity createBeginMessage(Collection<LogEntity> data) {
        MsgEntity msg = new MsgEntity();
        msg.setMsg_type(DataCmdType.TRACES_SEND_BEGIN);
        msg.setData_total(data.size());
        return msg;
    }

    public static MsgEntity createEndMessage(Collection<LogEntity> data) {
        MsgEntity msg = new MsgEntity();
        msg.setMsg_type(DataCmdType.TRACES_SEND_END);
        msg.setData_total(data.size());
        return msg;
    }
}
