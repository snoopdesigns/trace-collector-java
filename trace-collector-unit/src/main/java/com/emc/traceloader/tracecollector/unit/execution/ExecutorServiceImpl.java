package com.emc.traceloader.tracecollector.unit.execution;

import com.emc.traceloader.tracecollector.unit.execution.ptrace.PtraceExecutorService;
import com.emc.traceloader.tracecollector.unit.execution.ptrace.PtraceExecutorServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ExecutorServiceImpl implements ExecutorService{

    @Override
    public PtraceExecutorService ptraceExecutorInstance() {
        return new PtraceExecutorServiceImpl(this);
    }

    public void executeCmd(String cmd) throws IOException, InterruptedException {
        String[] command = new String[1];
        if(OSValidator.isWindows()) {
            command[0] = "cmd";
        } else if(OSValidator.isUnix()) {
            command[0] = "bash";
        } else {
            command[0] = "cmd";
        }
        Process p = Runtime.getRuntime().exec(command);
        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
        new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
        PrintWriter stdin = new PrintWriter(p.getOutputStream());
        stdin.println(cmd);
        stdin.close();
        int returnCode = p.waitFor();
        System.out.println("Return code = " + returnCode);
    }

    private class SyncPipe implements Runnable
    {
        public SyncPipe(InputStream istrm, OutputStream ostrm) {
            istrm_ = istrm;
            ostrm_ = ostrm;
        }
        public void run() {
            try
            {
                final byte[] buffer = new byte[1024];
                for (int length = 0; (length = istrm_.read(buffer)) != -1; )
                {
                    ostrm_.write(buffer, 0, length);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        private final OutputStream ostrm_;
        private final InputStream istrm_;
    }
}
