package uk.m4xy.dataapi.api.data.element;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.element.exception.DataUnmodifiableException;
import uk.m4xy.dataapi.api.data.element.type.DataField;

import java.lang.reflect.Type;

public interface DataElement<T extends DataType<T, ?, D>, D extends Data<T, ?, D>, E> {

    @NotNull E get(@NotNull D data) throws DataNotLoadedException;

    @NotNull Type getType();

    @NotNull DataField<E> getDataField(@NotNull D data);

    void setDataField(@NotNull D data, @Nullable E value) throws DataUnmodifiableException;

}
