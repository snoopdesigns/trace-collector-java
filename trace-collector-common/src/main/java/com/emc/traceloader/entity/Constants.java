package com.emc.traceloader.entity;

public class Constants {

    public enum ControlCmdType {
        START_COLLECTING,
        STOP_COLLECTING,
        SEND
    }

    public enum Politics {
        SEND_ON_END,
        SEND_CONTINIOUS
    }

    public enum DataCmdType {
        TRACES_SEND_BEGIN,
        TRACES_SEND_DATA,
        TRACES_SEND_END
    }
}
