package com.example.dipexample;

import com.couchbase.client.java.kv.UpsertOptions;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

//@Repository
public class CouchbaseCacheRepository implements CacheRepository {
    private final CouchbaseTemplate couchbaseTemplate;

    public CouchbaseCacheRepository(CouchbaseTemplate couchbaseTemplate) {
        this.couchbaseTemplate = couchbaseTemplate;
    }

    @Override
    public <T> T findObject(String key, Class<T> tClass) {
        return couchbaseTemplate.getCouchbaseClientFactory()
                .getCluster()
                .bucket(couchbaseTemplate.getBucketName())
                .defaultCollection()
                .get(key)
                .contentAs(tClass);
    }

    @Override
    public void save(String key, Object value, Duration expiryTime) {
        couchbaseTemplate.getCouchbaseClientFactory()
                .getCluster()
                .bucket(couchbaseTemplate.getBucketName())
                .defaultCollection()
                .upsert(key, value, UpsertOptions.upsertOptions().expiry(expiryTime));
    }
}
