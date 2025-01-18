package com.example.dipexample;

import java.time.Duration;

public interface CacheRepository {
     <T> T findObject(String key, Class<T> tClass);

    void save(String key, Object value, Duration expiryTime);
}
