package com.emc.traceloader.tracecollector.unit.execution.ptrace;

import com.emc.traceloader.tracecollector.unit.execution.ExecutorService;

import java.util.logging.Logger;

public class PtraceExecutorServiceImpl implements PtraceExecutorService{

    private static final Logger logger = Logger.getLogger(PtraceExecutorServiceImpl.class.toString());

    private static final String START_CMD = "java -jar test.jar start f1.txt"; //need to change to pptrace start command
    private static final String STOP_CMD = "dir";  //need to change to pptrace stop command

    private ExecutorService executorService;

    public PtraceExecutorServiceImpl(ExecutorService execService) {
        this.executorService = execService;
    }

    @Override
    public void startCollecting() {
        logger.info("Attepmpt to start Ptrace..");
        try {
            executorService.executeCmd(START_CMD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopCollecting() {
        logger.info("Attepmpt to stop Ptrace..");
        try {
            executorService.executeCmd(STOP_CMD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
