package com.momotalk_v1.utils;

import java.util.UUID;

public class NumberUUidGenerator{
    public static String numberUUID(){
        String uid= UUID.randomUUID().toString();
        int nuid=uid.hashCode();
        if (nuid<0)
            return 1+String.valueOf(-1*nuid);
        return String.valueOf(nuid);
    }
}
