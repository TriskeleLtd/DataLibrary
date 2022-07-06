package uk.m4xy.dataapi.api.util;

import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisPool;

public class RedisInformation {

    @NotNull public static RedisInformation get() {
        return null; // TODO
    }

    private  String password;

    public JedisPool createPool() {
        // TODO
        return null;
    }

    public String getChannel() {
        return "";
    }
}
