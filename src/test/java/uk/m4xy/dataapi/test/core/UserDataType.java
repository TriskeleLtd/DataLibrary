package uk.m4xy.dataapi.test.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.cache.DataCache;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.element.type.DataField;
import uk.m4xy.dataapi.api.data.persist.DataLoader;
import uk.m4xy.dataapi.api.data.persist.DataSaver;

import java.util.UUID;

public class UserDataType implements DataType<UserDataType, UUID, User> {

    DataField<UUID> keyField;
    DataField<Long> idField;

    @NotNull
    @Override
    public UUID getKey(@NotNull User data) throws DataNotLoadedException {
        return keyField.get();
    }

    @Override
    public long getId(@NotNull User data) throws DataNotLoadedException {
        return idField.get();
    }

    @Nullable
    @Override
    public DataCache<UserDataType, UUID, User> getDataCache() {
        return null;
    }

    @Nullable
    @Override
    public DataLoader<UserDataType, UUID, User> getDataLoader() {
        return null;
    }

    @Nullable
    @Override
    public DataSaver<UserDataType, UUID, User> getDataSaver() {
        return null;
    }

}
