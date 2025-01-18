package com.example.dipexample;

import com.couchbase.client.core.error.BucketNotFoundException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.env.ClusterEnvironment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.CouchbaseCustomConversions;
import org.springframework.data.couchbase.core.mapping.CouchbaseMappingContext;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;

import java.util.Collections;

//@Configuration

public class CouchbaseConfig extends AbstractCouchbaseConfiguration {
    @Value("#{systemEnvironment['DB_CONN_STR'] ?: '${spring.couchbase.bootstrap-hosts:localhost}'}")
    private String host;

    @Value("#{systemEnvironment['DB_USERNAME'] ?: '${spring.couchbase.bucket.user:Administrator}'}")
    private String username;

    @Value("#{systemEnvironment['DB_PASSWORD'] ?: '${spring.couchbase.bucket.password:password}'}")
    private String password;

    @Value("${spring.couchbase.bucket.name:sample}")
    private String bucketName;

    @Override
    public String getConnectionString() {
        return host;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }

    @Override
    public String typeKey() {
        return "type";
    }

    @Override
    @Bean(destroyMethod = "disconnect")
    public Cluster couchbaseCluster(ClusterEnvironment couchbaseClusterEnvironment) {
        try {
            return Cluster.connect(getConnectionString(), getUserName(), getPassword());
        } catch (Exception e) {
            throw e;
        }
    }

    @Bean
    public Bucket getCouchbaseBucket(Cluster cluster) {
        try {
            if (!cluster.buckets().getAllBuckets().containsKey(getBucketName())) {
                throw new BucketNotFoundException(bucketName);
            }
            return cluster.bucket(getBucketName());
        } catch (Exception e) {
            throw e;
        }
    }

    // CustomConversions 빈을 우선 사용하도록 @Primary 설정
    @Bean
    @Primary
    public CouchbaseCustomConversions customConversions() {
        // Couchbase에 맞는 CustomConversions 설정
        return new CouchbaseCustomConversions(Collections.emptyList());
    }


}
