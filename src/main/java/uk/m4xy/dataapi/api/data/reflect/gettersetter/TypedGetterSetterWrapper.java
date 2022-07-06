package uk.m4xy.dataapi.api.data.reflect.gettersetter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public final class TypedGetterSetterWrapper<O, E> implements ReflectiveGetterSetter<O, E> {
    private final Method getter;
    private final Method setter;
    private final Type type;

    public TypedGetterSetterWrapper(Method getter, Method setter) {
        this.getter = getter;
        this.setter = setter;

        this.type = getter.getReturnType();
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable E get(@NotNull O object) {
        try {
            return (E) getter.invoke(object);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void set(@NotNull O object, @Nullable E t) {
        try {
            setter.invoke(object, t);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Method getGetter() {
        return getter;
    }

    public Method getSetter() {
        return setter;
    }

    public Type getType() {
        return type;
    }
}
