package com.momotalk_v1.service.data;

import java.util.List;
import com.momotalk_v1.entity.Message;
import org.springframework.beans.factory.annotation.Value;

public interface MessagePool {
    @Value("momotalk.num:10")
    int num=10;
    int DELETE=1010;
    int EDIT=1020;
    public void add(Message message);

    public List<Message> fetch(String groupId, long start);
    public void close(String groupId);
    public void delayExpire(String groupId);
//    public boolean modify(Message message, int operation);
//    public boolean exists(String groupId);
}
