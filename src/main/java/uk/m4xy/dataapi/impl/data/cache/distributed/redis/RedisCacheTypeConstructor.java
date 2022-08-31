package uk.m4xy.dataapi.impl.data.cache.distributed.redis;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.DataTypeInitializer;
import uk.m4xy.dataapi.api.data.annotation.type.TypeConstructor;
import uk.m4xy.dataapi.api.data.cache.CacheExpiry;
import uk.m4xy.dataapi.impl.TypeInjector;

public class RedisCacheTypeConstructor implements TypeConstructor<AnnotatedRedisCache> {
    @Override
    public <T extends DataType<T, K, D>, K, D extends Data<T, K, D>>
    void apply(@NotNull AnnotatedRedisCache annotation,
               @NotNull DataTypeInitializer<T, K, D> initializer) {

        initializer.setDataCache(
                new RedisCache<>(
                        initializer.getKeyType(),
                        TypeInjector.get(annotation.distribution()),
                        new CacheExpiry(annotation.cacheExpiry()),
                        TypeInjector.get(annotation.serverPath()),
                        annotation.channel()
                ));
    }
}
