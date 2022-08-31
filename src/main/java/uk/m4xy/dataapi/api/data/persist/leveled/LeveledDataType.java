package uk.m4xy.dataapi.api.data.persist.leveled;

import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.cache.DataCache;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.persist.DataSaver;

public abstract class LeveledDataType<T extends LeveledDataType<T, K, D, L>, K, D extends Data<T, K, D>, L extends Enum<L> & LoadLevel> implements DataType<T, K, D> {

    @Nullable
    private final DataCache<T, K, D> dataCache;

    public LeveledDataType(@Nullable DataCache<T, K, D> dataCache) {
        this.dataCache = dataCache;
    }

    protected abstract void registerOtherElements();

    public abstract DataElement<T, D, Long> getIdElement();
    public abstract DataElement<T, D, K> getKeyElement();

    public void registerElement(DataElement<T, D, ?> dataElement, L loadLevel) {
        // TODO
    }

    public void registerLevel(L loadLevel, DataElement<T, D, ?>... elements) {
        // TODO
    }

    @Override
    public @Nullable DataCache<T, K, D> getDataCache() {
        return this.dataCache;
    }

    @Override
    public @Nullable LeveledDataLoader<T, K, D, L> getDataLoader() {
        return null;
    }

    @Override
    public @Nullable DataSaver<T, K, D> getDataSaver() {
        return null;
    }
}
