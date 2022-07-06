package uk.m4xy.dataapi.api.data.reflect.gettersetter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.element.exception.DataUnmodifiableException;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;
import uk.m4xy.dataapi.api.util.GetterSetter;

public sealed interface ReflectiveGetterSetter<O, E> extends GetterSetter<O, E> permits TypedFieldWrapper, TypedGetterSetterWrapper, ReflectiveDataElementWrapper {
    @SuppressWarnings("unchecked")
    static <O extends ReflectedDataObject<?, ?, O, ?>, E> Class<E> getElementType(ReflectiveGetterSetter<O, E> reflectiveGetterSetter) {
        return (Class<E>) switch (reflectiveGetterSetter) {
            case TypedFieldWrapper<O, E> wrapper -> wrapper.getField().getType();
            case TypedGetterSetterWrapper<O, E> wrapper -> wrapper.getGetter().getReturnType();
            case ReflectiveDataElementWrapper<O, E> wrapper -> getElementType(wrapper.getReflectiveGetterSetter());
        };
    }

    @Nullable
    E get(@NotNull O object);

    void set(@NotNull O object, @Nullable E e) throws DataUnmodifiableException;
}
