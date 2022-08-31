package uk.m4xy.dataapi.impl.data.abstracted;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.annotation.Id;
import uk.m4xy.dataapi.api.data.annotation.Key;

import java.lang.reflect.Field;

public abstract class AbstractDataType<T extends AbstractDataType<T, K, D, E>, K, D extends AbstractDataStore<T, K, D>, E> implements DataType<T, K, D> {

    private final Class<E> sourceType;

    private int idElement, keyElement;
    //private final Map<Integer, AbstractDataElement<T, D, K>> elementMap;

    public AbstractDataType(@NotNull Class<E> sourceType) {
        this.sourceType = sourceType;

        //this.elementMap = Maps.newConcurrentMap();
        int id = 0;
        for (Field field : sourceType.getFields()) {

            if (field.isAnnotationPresent(Key.class)) {
                this.keyElement = id;
            }

            if (field.isAnnotationPresent(Id.class)) {
                this.idElement = id;
            }

            //this.elementMap.put(id, new AbstractDataElement<>(id, field.getType()));
            id++;
        }
    }

    @NotNull
    public AbstractDataStore<T, K, D> from(@NotNull E object) {
        return new AbstractDataStore<>(this.idElement, this.keyElement, object);
    }

    @NotNull
    @Override
    public K getKey(@NotNull D data) throws DataNotLoadedException {
        return data.getKey();
    }

    @Override
    public long getId(@NotNull D data) throws DataNotLoadedException {
        return data.getId();
    }

    @NotNull
    public Class<E> getSourceType() {
        return this.sourceType;
    }
}
