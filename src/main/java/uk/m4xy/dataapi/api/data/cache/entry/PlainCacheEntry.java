package uk.m4xy.dataapi.api.data.cache.entry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.cache.CacheExpiry;
import uk.m4xy.dataapi.api.data.cache.exception.CacheExpiredException;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PlainCacheEntry<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> implements CacheEntry<T, K, D> {

    protected final ReentrantReadWriteLock readWriteLock;

    @NotNull
    protected final CacheExpiry cacheExpiry;
    @NotNull
    protected transient final AtomicLong timeChanged;
    private final long id;
    @NotNull
    private final K key;
    @Nullable
    private transient D data;

    public PlainCacheEntry(D data) {
        this(CacheExpiry.NEVER, data.getId(), data.getKey(), data);
    }

    public PlainCacheEntry(long id,
                           @NotNull K key,
                           @Nullable D data) {
        this(CacheExpiry.NEVER, id, key, data);
    }

    public PlainCacheEntry(@NotNull CacheExpiry cacheExpiry, D data) {
        this(cacheExpiry, data.getId(), data.getKey(), data);
    }

    public PlainCacheEntry(@NotNull CacheExpiry cacheExpiry,
                           long id,
                           @NotNull K key,
                           @Nullable D data) {
        this.readWriteLock = new ReentrantReadWriteLock();

        this.cacheExpiry = cacheExpiry;

        this.id = id;
        this.key = key;
        this.data = data;

        this.timeChanged = new AtomicLong(this.data == null ? 0 : System.currentTimeMillis());
    }


    @Override
    public @NotNull D get() throws CacheExpiredException {
        this.readWriteLock.readLock().lock();

        try {
            if (this.data == null || this.isExpired()) {
                throw new CacheExpiredException();
            }

            return this.data;
        } finally {
            this.readWriteLock.readLock().unlock();
        }
    }

    @Override
    public boolean isExpired() {
        return this.isExpired(this.cacheExpiry);
    }

    @Override
    public @NotNull K getKey() {
        return this.key;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public long getTimeChanged() {
        return this.timeChanged.get();
    }

    @Override
    public void update(D newData) {
        this.readWriteLock.writeLock().lock();

        try {
            this.data = newData;
            this.timeChanged.set(System.currentTimeMillis());
        } finally {
            this.readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void expire() {
        this.readWriteLock.writeLock().lock();

        try {
            this.data = null;
        } finally {
            this.readWriteLock.writeLock().unlock();
        }
    }
}
