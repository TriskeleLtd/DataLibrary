package uk.m4xy.dataapi.api.data.reflect;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.reflect.exception.ReflectObjectMissingError;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetterPointer;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveDataElementWrapper;

public record FieldPointer
        <O extends ReflectedDataObject<?, ?, O, ?>, E>
        (@NotNull Class<O> dataClass, @NotNull String fieldName)
        implements ReflectiveGetterSetterPointer<O, E> {


    @Override
    public @NotNull ReflectiveDataElementWrapper<O, E> get() {
        try {
            return new ReflectiveDataElementWrapper<>(this.dataClass.getField(this.fieldName));
        } catch (NoSuchFieldException e) {
            throw new ReflectObjectMissingError(this.dataClass, this);
        }
    }
}
