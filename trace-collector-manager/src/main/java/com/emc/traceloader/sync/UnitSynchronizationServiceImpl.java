package com.emc.traceloader.sync;

import com.emc.traceloader.db.entity.Host;
import com.emc.traceloader.httputils.HttpUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UnitSynchronizationServiceImpl implements UnitSynchronizationService {

    @Override
    public boolean checkUnitAvailable(Host host) {
        URL unitUrl = null;
        try {
            unitUrl = new URL(HttpUtils.buildURLForSync(host, this.generateSyncId(host)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return HttpUtils.checkURLAvailable(unitUrl);
    }

    @Override
    public String generateSyncId(Host host) {
        return this.makeSHA1Hash(host.getIp() + ":" + host.getPort());
    }

    public String makeSHA1Hash(String input) {
        String hexStr = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = input.getBytes();
            md.update(buffer);
            byte[] digest = md.digest();


            for (int i = 0; i < digest.length; i++) {
                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexStr;
    }
}
