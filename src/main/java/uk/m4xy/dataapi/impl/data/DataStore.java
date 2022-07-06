package uk.m4xy.dataapi.impl.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.element.exception.DataUnmodifiableException;
import uk.m4xy.dataapi.api.data.element.type.DataField;
import uk.m4xy.dataapi.api.data.element.type.ModifiableDataField;

import java.util.Map;

public class DataStore<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> implements Data<T, K, D> {
    private final long id;
    private final K key;
    private final Map<DataElement<T, D, ?>, DataField> dataFieldMap;

    public DataStore(long id, K key, Map<DataElement<T, D, ?>, DataField> dataFieldMap) {
        this.key = key;
        this.id = id;
        this.dataFieldMap = dataFieldMap;
    }

    @Override
    public @NotNull K getKey() {
        if (this.key == null) {
            throw new DataNotLoadedException();
        }

        return this.key;
    }

    @Override
    public long getId() {
        if (this.id == -1) {
            throw new DataNotLoadedException();
        }

        return this.id;
    }

    @Override
    public @Nullable <E> DataField getDataField(@NotNull DataElement<T, D, E> dataElement) {
        return this.dataFieldMap.get(dataElement);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> void setDataField(@NotNull DataElement<T, D, E> dataElement, @Nullable E value) {
        final DataField dataField = this.getDataField(dataElement);

        if (dataField == null) {
            this.dataFieldMap.put(dataElement, dataElement.newImplementation(value));
        } else {
            if (!dataField.isSettable()) throw new DataUnmodifiableException(dataElement);

            final ModifiableDataField<E> modifiableDataField = (ModifiableDataField<E>) dataField;
            modifiableDataField.set(value);
        }
    }
}
