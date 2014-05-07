package com.emc.traceloader.tracecollector.unit.fileutils;

import com.emc.traceloader.keeper.api.LogEntity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class CDBLogFileParser {

    private static final Logger logger = Logger.getLogger(CDBLogFileParser.class.toString());

    public Collection<LogEntity> getTraces(String filename) throws FileNotFoundException, IOException {
        Collection<LogEntity> result = new ArrayList<LogEntity>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            result.add(this.parseTraceLine(line));
        }
        br.close();
        return result;
    }


    /*dumb function to parse file contents*/
    private LogEntity parseTraceLine(String line) {
        LogEntity result = new LogEntity();
        String datetime = line.substring(line.indexOf("::") + 2, line.indexOf("[mpx]") - 1);
        String deviceId = line.substring(line.indexOf("LUNWWN(") + 7, line.length() - 1);
        String cdbString = line.substring(line.indexOf("CDB(") + 4, line.indexOf("LUNWWN") - 2);
        if(cdbString.length() == 20) {  //20x 16hex symbols
            String op_code = cdbString.substring(0,2);
            String op_address = cdbString.substring(4,12);
            String transfer_length = cdbString.substring(14,18);
            result.setDatetime(datetime);
            result.setDevice_id(deviceId);
            result.setOp_address(op_address);
            result.setOp_code(op_code);
            result.setTransfer_length(transfer_length);
        }
        return result;
    }
}
