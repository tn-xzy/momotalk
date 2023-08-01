package com.momotalk_v1.utils;

import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static String sha1(InputStream is) {
        try (InputStream data=is){
            MessageDigest digest =MessageDigest.getInstance("SHA1");
            byte[] buffer=new byte[1024];
            int len;
            while ((len=data.read(buffer))>=0){
                digest.update(buffer,0,len);
            }
            return Hex.toHexString(digest.digest());
        }catch (IOException | NoSuchAlgorithmException e){

        }
        return null;
    }

}
