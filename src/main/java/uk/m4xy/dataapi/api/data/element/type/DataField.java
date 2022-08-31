package uk.m4xy.dataapi.api.data.element.type;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;

public sealed interface DataField<E> permits ImmutableDataField, ModifiableDataField {

    boolean isSettable();

    @NotNull
    E get() throws DataNotLoadedException;

}
