package com.ssafy.project.common.provider;


import java.util.concurrent.TimeUnit;

public interface RedisProvider {

    void set(String key, Object value, long time, TimeUnit timeUnit);
    Object get(String key);
    boolean delete(String key);
    boolean hasKey(String key);
    void setBlackList(String key, Object value, long time, TimeUnit timeUnit);
    Object getBlackList(String key);
    boolean deleteBlackList(String key);
    boolean hasKeyBlackList(String key);

//    void addToSet(String key, Object value);
//    void removeFromSet(String key, Object value);
//    Set<Object> getSet(String key);
//    boolean hasKeyFromSet(String key, String value);
}

