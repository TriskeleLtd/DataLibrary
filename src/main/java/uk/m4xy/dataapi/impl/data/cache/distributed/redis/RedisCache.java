package uk.m4xy.dataapi.impl.data.cache.distributed.redis;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.cache.CacheExpiry;
import uk.m4xy.dataapi.api.data.cache.distributed.DistributedCache;
import uk.m4xy.dataapi.api.data.cache.entry.CacheEntry;
import uk.m4xy.dataapi.api.data.cache.exception.KeyNotInCacheException;
import uk.m4xy.dataapi.api.data.cache.maintenance.CacheMaintainer;
import uk.m4xy.dataapi.api.distributions.Distribution;
import uk.m4xy.dataapi.api.type.StringTypeConverter;
import uk.m4xy.dataapi.api.util.RedisInformation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RedisCache<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> extends JedisPubSub implements DistributedCache<T, K, D> {
    private final ReentrantReadWriteLock lock;
    private final Class<K> keyType;
    private final Map<K, CacheEntry<T, K, D>> keyCache;
    private final Map<Long, CacheEntry<T, K, D>> idCache;
    private final CacheExpiry cacheExpiry;
    private final String channel;
    private final JedisPool jedisPool;

    private final Distribution distribution;

    public RedisCache(Class<K> keyType, Distribution distribution, CacheExpiry cacheExpiry, RedisInformation redisInformation) {
        this.keyType = keyType;
        this.distribution = distribution;

        this.keyCache = new ConcurrentHashMap<>();
        this.idCache = new ConcurrentHashMap<>();

        this.cacheExpiry = cacheExpiry;

        this.jedisPool = redisInformation.createPool();

        this.channel = redisInformation.getChannel();

        this.lock = new ReentrantReadWriteLock();

        this.registerMaintenance(this);
    }

    @Override
    public void cache(@NotNull CacheEntry<T, K, D> cacheEntry) {
        this.lock.writeLock().lock();

        try {
            this.idCache.put(cacheEntry.getId(), cacheEntry);
            this.keyCache.put(cacheEntry.getKey(), cacheEntry);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public @Nullable D get(@NotNull K key) {
        this.lock.readLock().lock();

        final D data;
        try {
            final CacheEntry<T, K, D> cacheEntry = this.keyCache.get(key);
            if (cacheEntry == null) {
                throw new KeyNotInCacheException(key);
            }

            data = cacheEntry.get();
        } finally {
            this.lock.readLock().unlock();
        }

        return data;
    }

    @Override
    public @Nullable D get(long id) {
        this.lock.readLock().lock();

        final D data;
        try {
            final CacheEntry<T, K, D> cacheEntry = this.idCache.get(id);
            if (cacheEntry == null) {
                throw new KeyNotInCacheException(id);
            }

            data = cacheEntry.get();
        } finally {
            this.lock.readLock().unlock();
        }

        return data;
    }

    @Override
    public @NotNull CacheExpiry getCacheExpiry() {
        return this.cacheExpiry;
    }

    @Override
    public void maintain() {
        CacheMaintainer.purgeExpired(this.idCache, this.lock);
        CacheMaintainer.purgeExpired(this.keyCache, this.lock);
    }

    @Override
    public void expireElsewhere(@NotNull K key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.publish(this.channel, distribution.getUniqueIdentifier() + ":key:" + StringTypeConverter.serializeObject(key));
        }
    }

    @Override
    public void expireElsewhere(long id) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.publish(this.channel, distribution.getUniqueIdentifier() + ":id:" + id);
        }
    }

    @Override
    public void expireLocally(@NotNull K key) {
        this.lock.writeLock().lock();
        try {
            final CacheEntry<T, K, D> keyEntry = this.keyCache.get(key);

            if (keyEntry != null) {
                keyEntry.expire();
                this.idCache.get(keyEntry.getId()).expire();
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public void expireLocally(long id) {
        this.lock.writeLock().lock();
        try {
            final CacheEntry<T, K, D> idEntry = this.idCache.get(id);

            if (idEntry != null) {
                idEntry.expire();
                this.keyCache.get(idEntry.getKey()).expire();
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    // Jedis Message Handler

    @Override
    public void onMessage(String channel, String message) {
        /*
        Use a simple format:
        distributionidentifier:action:data
         */

        if (!channel.equals(this.channel)) {
            return;
        }

        final String[] split = message.split(":", 3);
        final String distributionId = split[0];

        if (distributionId.equals(this.distribution.getUniqueIdentifier())) {
            // Ignore messages that we sent
            return;
        }

        if ("id".equals(split[1])) {
            this.expireLocally(Long.parseLong(split[2]));
        } else { // "key"
            this.expireLocally(StringTypeConverter.deserializeObject(split[3], keyType));
        }
    }

}
