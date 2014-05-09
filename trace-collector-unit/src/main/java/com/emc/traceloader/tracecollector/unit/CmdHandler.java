package com.emc.traceloader.tracecollector.unit;

import com.emc.traceloader.tracecollector.unit.execution.ExecutorService;
import com.emc.traceloader.tracecollector.unit.execution.ExecutorServiceImpl;
import com.emc.traceloader.tracecollector.unit.fileutils.CDBLogFileParser;
import com.emc.traceloader.tracecollector.unit.postutils.PostTask;
import com.emc.traceloader.unit.api.CmdEntity;
import com.emc.traceloader.unit.api.ControlCmdType;

import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

public class CmdHandler {

    private static final Logger logger = Logger.getLogger(CmdHandler.class.toString());
    private ExecutorService executorService = new ExecutorServiceImpl();
    private CDBLogFileParser parser = new CDBLogFileParser();
    private ScheduledExecutorService threadExecutor = Executors.newSingleThreadScheduledExecutor();

    public void handleExternalCommand(CmdEntity cmd) {
        if(cmd.getCmd_type() == ControlCmdType.START_COLLECTING) {
            logger.info("Start collecting requested!");
            executorService.ptraceExecutorInstance().startCollecting();   //need to check SEND_POLITIC
        } else if(cmd.getCmd_type() == ControlCmdType.STOP_COLLECTING) {
            logger.info("Stop collecting requested!");
            executorService.ptraceExecutorInstance().stopCollecting();
        } else if(cmd.getCmd_type() == ControlCmdType.SEND) {
            try {
                PostTask task = new PostTask(new URL(cmd.getPostback_ip()),
                        cmd.getLog_entity_per_msg(), parser.getTraces("f1.txt"), cmd.getSend_interval());
                threadExecutor.execute(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void destroy() {
        threadExecutor.shutdownNow();
    }
}
