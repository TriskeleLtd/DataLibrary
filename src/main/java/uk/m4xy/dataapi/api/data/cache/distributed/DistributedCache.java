package uk.m4xy.dataapi.api.data.cache.distributed;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.cache.DataCache;

public interface DistributedCache<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> extends DataCache<T, K, D> {

    void expireElsewhere(@NotNull K key);

    void expireElsewhere(long id);

    default void expireElsewhere(@NotNull D data) {
        this.expireElsewhere(data.getId());
    }

    void expireLocally(@NotNull K key);

    void expireLocally(long id);

    default void expireLocally(@NotNull D data) {
        this.expireLocally(data.getId());
    }

    @Override
    default void expire(@NotNull K key) {
        this.expireElsewhere(key);
        this.expireLocally(key);
    }

    @Override
    default void expire(long id) {
        this.expireElsewhere(id);
        this.expireLocally(id);
    }

}
