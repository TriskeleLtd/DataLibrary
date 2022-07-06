package uk.m4xy.dataapi.impl.data.reflection.element;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.element.type.DataField;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetter;
import uk.m4xy.dataapi.impl.data.reflection.ReflectiveDataField;
import uk.m4xy.dataapi.impl.data.reflection.ReflectiveDataType;

import java.lang.reflect.Type;

public class ReflectiveDataElement<T extends ReflectiveDataType<T, ?, O, ?>, O extends ReflectedDataObject<T, ?, O, ?>, E> implements DataElement<T, O, E> {

    private final Class<E> type;
    private final ReflectiveGetterSetter<O, E> getterSetter;

    public ReflectiveDataElement(ReflectiveGetterSetter<O, E> reflectiveGetterSetter) {
        this.getterSetter = reflectiveGetterSetter;

        this.type = ReflectiveGetterSetter.getElementType(reflectiveGetterSetter);
    }

    @Override
    public @NotNull E get(@NotNull O data) throws DataNotLoadedException {
        final E element = this.getterSetter.get(data);
        if (element == null) throw new DataNotLoadedException();
        return element;
    }

    @Override
    public @NotNull DataField newImplementation(@NotNull O object, @Nullable E value) {
        return new ReflectiveDataField<>(object, getterSetter);
    }

    @Override
    public @NotNull Type getType() {
        return this.type;
    }
}
