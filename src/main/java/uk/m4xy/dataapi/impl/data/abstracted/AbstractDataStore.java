package uk.m4xy.dataapi.impl.data.abstracted;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.element.type.DataField;
import uk.m4xy.dataapi.impl.data.sql.field.PrimitiveDataField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AbstractDataStore<T extends DataType<T, K, D>, K, D extends Data<T, K, D>> implements Data<T, K, D> {
    private final Map<Integer, DataField<?/*E*/>> dataFieldMap;

    private final int idId, keyId;

    public AbstractDataStore(int idId, int keyId, @NotNull Map<Integer, DataField<?/*E*/>> dataFieldMap) {
        this.idId = idId;
        this.keyId = keyId;
        this.dataFieldMap = dataFieldMap;
    }

    public AbstractDataStore(int idId, int keyId, @NotNull Object object) {
        this.idId = idId;
        this.keyId = keyId;
        int i = 0;
        this.dataFieldMap = new HashMap<>();
        for (Field field : object.getClass().getFields()) {
            this.dataFieldMap.put(i, fieldFrom(field, object));
        }
    }

    private DataField<?> fieldFrom(Field field, Object object) {
        return new PrimitiveDataField<>(() -> {
            try {
                return field.get(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }, v -> {
            try {
                field.set(object, v);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }


    @SuppressWarnings("unchecked")
    @NotNull
    <E> DataField<E> getDataField(int elementId) {
        return (DataField<E>) this.dataFieldMap.get(elementId);
    }


    @Override
    @SuppressWarnings("unchecked")
    public @NotNull K getKey() {
        return (K) this.getDataField(this.keyId).get();
    }

    @Override
    public long getId() {
        return (long) this.getDataField(this.idId).get();
    }

}
