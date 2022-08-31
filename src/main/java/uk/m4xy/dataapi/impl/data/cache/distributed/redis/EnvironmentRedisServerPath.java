package uk.m4xy.dataapi.impl.data.cache.distributed.redis;

import dev.triskele.common.environment.annotation.EnvironmentPath;
import org.jetbrains.annotations.NotNull;

/**
 * Fetches the Redis Details from the Environment variables
 */
public interface EnvironmentRedisServerPath extends RedisServerPath {

    @NotNull
    @Override
    @EnvironmentPath("REDIS_HOST")
    String getHost();

    @NotNull
    @Override
    @EnvironmentPath("REDIS_USER")
    String getUser();

    @NotNull
    @Override
    @EnvironmentPath("REDIS_PASSWORD")
    String getPassword();

    @Override
    @EnvironmentPath("REDIS_PORT")
    int getPort();

}
