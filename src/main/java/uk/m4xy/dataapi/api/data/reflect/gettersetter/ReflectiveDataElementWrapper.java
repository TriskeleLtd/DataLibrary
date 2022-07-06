package uk.m4xy.dataapi.api.data.reflect.gettersetter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetter;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.TypedFieldWrapper;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.TypedGetterSetterWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectiveDataElementWrapper<O, E> implements ReflectiveGetterSetter<O, E> {
    private final ReflectiveGetterSetter<O, E> reflectiveGetterSetter;

    public ReflectiveDataElementWrapper(Field field) {
        this.reflectiveGetterSetter = new TypedFieldWrapper<>(field);
    }

    public ReflectiveDataElementWrapper(Method getter, Method setter) {
        this.reflectiveGetterSetter = new TypedGetterSetterWrapper<>(getter, setter);
    }

    public void set(@NotNull O object, @Nullable E value) {
        this.reflectiveGetterSetter.set(object, value);
    }

    public @NotNull E get(@NotNull O object) throws DataNotLoadedException {
        final E data = this.reflectiveGetterSetter.get(object);
        if (data == null) {
            throw new DataNotLoadedException();
        }

        return data;
    }

    public ReflectiveGetterSetter<O, E> getReflectiveGetterSetter() {
        return reflectiveGetterSetter;
    }
}
