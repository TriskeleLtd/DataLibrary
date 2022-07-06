package uk.m4xy.dataapi.api.data.element.type;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;

public non-sealed interface ImmutableDataField<E> extends DataField {
    @NotNull E get() throws DataNotLoadedException;

    default boolean isSettable() {
        return false;
    }

}
