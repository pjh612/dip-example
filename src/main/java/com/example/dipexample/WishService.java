package com.example.dipexample;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Service
public class WishService {
    private final CacheRepository cacheRepository;

    public WishService(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }


    public Long getWishCount(String id) {
        String key = "WISHCOUNT:" + id;

        return cacheRepository.findObject(key, Long.class);
    }

    public void saveWishCount(String id, Long count) {
        String key = "WISHCOUNT:" + id;

        cacheRepository.save(key, count, Duration.of(60, ChronoUnit.SECONDS));
    }
}
