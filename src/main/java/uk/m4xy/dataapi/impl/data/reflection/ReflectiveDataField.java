package uk.m4xy.dataapi.impl.data.reflection;

import org.checkerframework.checker.units.qual.K;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.element.type.ModifiableDataField;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetter;
import uk.m4xy.dataapi.api.data.tree.LoadLevel;

public class ReflectiveDataField<
        T extends ReflectiveDataType<T, ?, O, ?>,
        O extends ReflectedDataObject<T, ?, O, ?>,
        E>
        implements ModifiableDataField<E> {

    private final O dataStore;
    private final ReflectiveGetterSetter<O, E> elementWrapper;

    public ReflectiveDataField(O dataStore, ReflectiveGetterSetter<O, E> elementWrapper) {
        this.dataStore = dataStore;
        this.elementWrapper = elementWrapper;
    }

    @Override
    public void set(@Nullable E value) {
        elementWrapper.set(dataStore, value);
    }

    @Override
    public @NotNull E get() throws DataNotLoadedException {
        final E element = elementWrapper.get(dataStore);
        if (element == null) {
            throw new DataNotLoadedException();
        }
        return element;
    }
}
