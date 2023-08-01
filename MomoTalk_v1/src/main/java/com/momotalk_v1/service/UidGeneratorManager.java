package com.momotalk_v1.service;

import com.hy.corecode.idgen.WFGIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class UidGeneratorManager {
    @Autowired
    private WFGIdGenerator wFGIdGenerator;
    public long nextId(String groupId){
        return wFGIdGenerator.next();
    }
}
