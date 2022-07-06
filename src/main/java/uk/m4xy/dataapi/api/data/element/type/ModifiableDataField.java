package uk.m4xy.dataapi.api.data.element.type;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;

public non-sealed interface ModifiableDataField<E> extends DataField {

    void set(@Nullable E value);

    @NotNull E get() throws DataNotLoadedException;

    @Override
    default boolean isSettable() {
        return true;
    }
}
