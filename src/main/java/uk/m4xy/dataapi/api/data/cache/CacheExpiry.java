package uk.m4xy.dataapi.api.data.cache;

import uk.m4xy.dataapi.api.data.cache.entry.CacheEntry;

public class CacheExpiry {

    public static final long NEVER_MILLIS = Long.MAX_VALUE / 2, TEN_MINUTES_MILLIS = 1000 * 10L;
    public static final CacheExpiry NEVER = new CacheExpiry(NEVER_MILLIS);
    public static final CacheExpiry TEN_MINUTES = new CacheExpiry(TEN_MINUTES_MILLIS);

    private long expiryTime;

    public CacheExpiry(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public boolean isExpired(CacheEntry<?, ?, ?> cacheEntry) {
        return cacheEntry.getTimeChanged() > this.expiryTime + System.currentTimeMillis();
    }

    public long getExpiryTime() {
        return this.expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }
}
