package uk.m4xy.dataapi.api.data.cache.exception;

public class KeyNotInCacheException extends NullPointerException {
    public KeyNotInCacheException(Object key) {
        super(key.toString());
    }

}
