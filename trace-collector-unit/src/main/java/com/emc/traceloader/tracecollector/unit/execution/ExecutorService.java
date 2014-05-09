package com.emc.traceloader.tracecollector.unit.execution;

import com.emc.traceloader.tracecollector.unit.execution.ptrace.PtraceExecutorService;

import java.io.IOException;

public interface ExecutorService {

    public PtraceExecutorService ptraceExecutorInstance();

    public void executeCmd(String cmd) throws IOException, InterruptedException;
}
