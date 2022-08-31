package uk.m4xy.dataapi.api.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.element.exception.DataUnmodifiableException;
import uk.m4xy.dataapi.api.data.element.type.DataField;
import uk.m4xy.dataapi.api.data.reference.Referenceable;

public interface Data<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> extends Referenceable {
    @Nullable
    @SuppressWarnings("unchecked")
    default <E> DataField<E> getDataField(@NotNull DataElement<T, D, E> dataElement) {
        return dataElement.getDataField((D) this);
    }

    @SuppressWarnings("unchecked")
    default <E> void setDataField(@NotNull DataElement<T, D, E> dataElement, @Nullable E value) throws DataUnmodifiableException {
        dataElement.setDataField((D) this, value);
    }

    @NotNull
    K getKey() throws DataNotLoadedException;

    long getId() throws DataNotLoadedException;
}
