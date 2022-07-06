package uk.m4xy.dataapi.api.data.reflect;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.reflect.exception.ReflectObjectMissingError;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetter;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetterPointer;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveDataElementWrapper;

public record GetterSetterMethodPointer<O extends ReflectedDataObject<?, ?, O, ?>, E>(@NotNull Class<O> dataClass, @NotNull String getterMethodName, @NotNull String setterMethodName) implements ReflectiveGetterSetterPointer<O, E> {
    @Override
    public @NotNull ReflectiveGetterSetter<O, E> get() {
        try {
            return new ReflectiveDataElementWrapper<>(dataClass.getMethod(this.getterMethodName), dataClass.getMethod(this.setterMethodName));
        } catch (NoSuchMethodException e) {
            throw new ReflectObjectMissingError(dataClass, this);
        }
    }
}
