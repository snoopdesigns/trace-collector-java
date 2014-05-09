package com.emc.traceloader;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.PrintWriter;
import java.util.Random;

public class TraceFileHandler {

    private static final String AB = "0123456789ABCDEF";
    private static Random random = new Random();

    public void generateTraces(String filename, int count) {
        try {
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        for(int i=0;i<count;i++) {
            writer.println(generateOneTraceLine());
        }
        writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateOneTraceLine() {
        StringBuffer sb = new StringBuffer();
        sb.append("[0]");
        sb.append("0004.");
        sb.append(randomString(4));
        sb.append("::");
        sb.append(generateRandomDatetime());
        sb.append(" ");
        sb.append("[mpx]PX-mpio Info:DsmLBGetPath: CDB(");
        sb.append(randomString(20));
        sb.append(") ");
        sb.append("LUNWWN(Symm 000192601698 vol 4080)");
        return sb.toString();
    }



    private String randomString(int len) {
        StringBuilder sb = new StringBuilder( len );
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(random.nextInt(AB.length())));
        return sb.toString();
    }

    private String generateRandomDatetime() {
        DateTime startTime = new DateTime(random.nextLong()).withMillisOfSecond(0);
        Minutes minimumPeriod = Minutes.TWO;
        int minimumPeriodInSeconds = minimumPeriod.toStandardSeconds().getSeconds();
        int maximumPeriodInSeconds = Hours.ONE.toStandardSeconds().getSeconds();
        Seconds randomPeriod = Seconds.seconds(random.nextInt(maximumPeriodInSeconds - minimumPeriodInSeconds));
        DateTime endTime = startTime.plus(minimumPeriod).plus(randomPeriod);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/2014-HH:mm:ss.025");
        return dateTimeFormatter.print(startTime);
    }


}
