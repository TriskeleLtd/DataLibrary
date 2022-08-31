package uk.m4xy.dataapi.api.data.element.type;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;

public non-sealed interface ModifiableDataField<E> extends DataField<E> {

    void set(@Nullable E value);

    @Override
    default boolean isSettable() {
        return true;
    }
}
