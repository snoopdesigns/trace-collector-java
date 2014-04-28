package com.emc.traceloader;

import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class MainClass
{
    private static final Logger logger = Logger.getLogger(MainClass.class.toString());

    private static TraceFileHandler traceHandler = new TraceFileHandler();

    public static void main( String[] args )
    {
        logger.info("Executing traces generating...");
        if(args.length > 0) {
            if(args[0].equals("start")) {
                logger.info("Generating 100 traces to file: " + args[1]);
                traceHandler.generateTraces(args[1], 100);
            } else if(args[0].equals("stop")) {
                logger.info("Stop collecting traces.");
            }
        } else {
            logger.info("Usage: java -jar trace-collector-execution-emulator.jar [start|stop] [filename]");
        }
    }
}
