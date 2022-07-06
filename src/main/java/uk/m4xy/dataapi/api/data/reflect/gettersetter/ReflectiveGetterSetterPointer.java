package uk.m4xy.dataapi.api.data.reflect.gettersetter;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;

public interface ReflectiveGetterSetterPointer<O extends ReflectedDataObject<?, ?, O, ?>, E> {
    @NotNull ReflectiveGetterSetter<O, E> get();

}
