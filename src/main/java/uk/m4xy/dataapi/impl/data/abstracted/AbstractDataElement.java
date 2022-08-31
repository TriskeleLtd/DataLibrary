package uk.m4xy.dataapi.impl.data.abstracted;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.element.exception.DataUnmodifiableException;
import uk.m4xy.dataapi.api.data.element.type.DataField;
import uk.m4xy.dataapi.api.data.element.type.ModifiableDataField;

import java.lang.reflect.Type;

public class AbstractDataElement<T extends DataType<T, ?, D>, D extends AbstractDataStore<T, ?, D>, E> implements DataElement<T, D, E> {

    private final int elementId;
    private final Type type;

    public AbstractDataElement(int elementId, @NotNull Type type) {
        this.elementId = elementId;
        this.type = type;
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public E get(@NotNull D data) throws DataNotLoadedException {
        return (E) data.getDataField(this.elementId).get();
    }

    @NotNull
    @Override
    public Type getType() {
        return this.type;
    }

    @NotNull
    @Override
    public DataField<E> getDataField(@NotNull D data) {
        return data.getDataField(this.elementId);
    }

    @Override
    public void setDataField(@NotNull D data, @Nullable E value) throws DataUnmodifiableException {
        final DataField<Object> dataField = data.getDataField(this.elementId);
        if (!dataField.isSettable()) {
            throw new DataUnmodifiableException(this);
        }

        ((ModifiableDataField<E>) dataField).set(value);
    }
}
