package uk.m4xy.dataapi.impl.data.cache.distributed.redis;

import dev.triskele.common.environment.annotation.EnvironmentPath;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisPool;

public interface RedisServerPath {

    @NotNull
    String getHost();

    @NotNull
    String getUser();

    @NotNull
    String getPassword();

    int getPort();

    default @NotNull JedisPool initializePool() {
        return new JedisPool(this.getHost(), this.getPort(), this.getUser(), this.getPassword());
    }


}
