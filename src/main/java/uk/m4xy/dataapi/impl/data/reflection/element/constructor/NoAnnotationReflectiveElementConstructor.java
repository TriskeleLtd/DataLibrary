package uk.m4xy.dataapi.impl.data.reflection.element.constructor;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetter;
import uk.m4xy.dataapi.impl.data.reflection.ReflectiveDataType;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.annotation.EmptyAnnotation;

import java.util.Collection;

public non-sealed interface NoAnnotationReflectiveElementConstructor<T extends ReflectiveDataType<T, ?, O, ?>, O extends ReflectedDataObject<T, ?, O, ?>> extends ReflectiveElementConstructor<EmptyAnnotation, T, O> {

    default @NotNull <E> Collection<DataElement<T, O, E>> constructDataElements(@NotNull ReflectiveGetterSetter<O, E> getterSetter, @NotNull EmptyAnnotation annotation) {
        return this.constructDataElements(getterSetter);
    }

    @Override
    @NotNull <E> Collection<DataElement<T, O, E>> constructDataElements(@NotNull ReflectiveGetterSetter<O, E> getterSetter);
}
