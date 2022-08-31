package uk.m4xy.dataapi.impl.data.cache.distributed.redis;

import uk.m4xy.dataapi.api.data.annotation.type.TypeConstructorAnnotation;
import uk.m4xy.dataapi.api.data.cache.CacheExpiry;

@TypeConstructorAnnotation(RedisCacheTypeConstructor.class)
public @interface AnnotatedRedisCache {
    long cacheExpiry() default CacheExpiry.NEVER_MILLIS;

    Class<? extends RedisServerPath> serverPath() default EnvironmentRedisServerPath.class;
}


