package uk.m4xy.dataapi.impl.data.cache.distributed.redis;

import uk.m4xy.dataapi.api.data.annotation.type.TypeConstructorAnnotation;
import uk.m4xy.dataapi.api.data.cache.CacheExpiry;
import uk.m4xy.dataapi.api.distributions.Distribution;
import uk.m4xy.dataapi.impl.distributions.EnvironmentDistribution;

import java.lang.annotation.*;

@TypeConstructorAnnotation(RedisCacheTypeConstructor.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AnnotatedRedisCache {
    long cacheExpiry() default CacheExpiry.NEVER_MILLIS;

    String channel();

    Class<? extends RedisServerPath> serverPath() default EnvironmentRedisServerPath.class;

    Class<? extends Distribution> distribution() default EnvironmentDistribution.class;
}


