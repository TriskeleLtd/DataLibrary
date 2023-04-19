package uk.m4xy.dataapi.test.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.element.exception.DataUnmodifiableException;
import uk.m4xy.dataapi.api.data.element.type.DataField;

import javax.inject.Inject;
import java.util.UUID;

public class User implements Data<UserDataType, UUID, User> {
    DataField<UUID> keyField;
    DataField<Long> idField;
    @Nullable
    @Override
    public <E> DataField<E> getDataField(@NotNull DataElement<UserDataType, User, E> dataElement) {
        return null;
    }

    @Override
    public <E> void setDataField(@NotNull DataElement<UserDataType, User, E> dataElement, @Nullable E value) throws DataUnmodifiableException {

    }

    @NotNull
    @Override
    public UUID getKey() throws DataNotLoadedException {
        return this.keyField.get();
    }

    @Override
    public long getId() throws DataNotLoadedException {
        return this.idField.get();
    }
}
