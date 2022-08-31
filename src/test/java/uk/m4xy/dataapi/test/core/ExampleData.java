package uk.m4xy.dataapi.test.core;


import uk.m4xy.dataapi.impl.data.cache.distributed.redis.AnnotatedRedisCache;

@AnnotatedRedisCache(channel = "EXAMPLE")
public interface ExampleData {

}
