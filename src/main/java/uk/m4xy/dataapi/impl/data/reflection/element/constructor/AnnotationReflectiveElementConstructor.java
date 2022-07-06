package uk.m4xy.dataapi.impl.data.reflection.element.constructor;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetter;
import uk.m4xy.dataapi.impl.data.reflection.ReflectiveDataType;

import java.lang.annotation.Annotation;
import java.util.Collection;

public non-sealed interface AnnotationReflectiveElementConstructor<A extends Annotation, T extends ReflectiveDataType<T, ?, O, ?>, O extends ReflectedDataObject<T, ?, O, ?>> extends ReflectiveElementConstructor<A, T, O> {

    @Override
    @NotNull <E> Collection<DataElement<T, O, E>> constructDataElements(@NotNull ReflectiveGetterSetter<O, E> getterSetter, @NotNull A annotation);

    @Override
    default @NotNull <E> Collection<DataElement<T, O, E>> constructDataElements(@NotNull ReflectiveGetterSetter<O, E> getterSetter) {
        throw new RuntimeException("This method is unsupported");
    }
}
