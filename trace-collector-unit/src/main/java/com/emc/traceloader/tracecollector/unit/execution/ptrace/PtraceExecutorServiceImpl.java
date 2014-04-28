package com.emc.traceloader.tracecollector.unit.execution.ptrace;

import com.emc.traceloader.tracecollector.unit.execution.ExecutorService;

import java.util.logging.Logger;

public class PtraceExecutorServiceImpl implements PtraceExecutorService{

    private static final Logger logger = Logger.getLogger(PtraceExecutorServiceImpl.class.toString());

    private ExecutorService executorService;

    public PtraceExecutorServiceImpl(ExecutorService execService) {
        this.executorService = execService;
    }

    @Override
    public void startCollecting() {
        logger.info("Attepmpt to call Ptrace..");
        try {
            executorService.executeCmd("java -jar test.jar start f1.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
