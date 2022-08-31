package uk.m4xy.dataapi.impl.data.reflection.element.constructor;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetter;
import uk.m4xy.dataapi.impl.data.reflection.ReflectiveDataType;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.annotation.EmptyAnnotation;

import java.lang.annotation.Annotation;
import java.util.Collection;

public sealed interface ReflectiveElementConstructor<A extends Annotation, T extends ReflectiveDataType<T, ?, O, ?>, O extends ReflectedDataObject<T, ?, O, ?>> permits NoAnnotationReflectiveElementConstructor, AnnotationReflectiveElementConstructor {

    @SafeVarargs
    @NotNull
    static <T extends ReflectiveDataType<T, ?, O, ?>, O extends ReflectedDataObject<T, ?, O, ?>> ReflectiveElementConstructor<?, T, O> combine(@NotNull ReflectiveElementConstructor<?, T, O>... elementConstructors) {
        return new CombinedElementConstructor<>(elementConstructors);
    }

    @SuppressWarnings("unchecked")
    @NotNull
    default <E> Collection<DataElement<T, O, E>> constructDataElements(@NotNull ReflectiveGetterSetter<O, E> getterSetter) {
        return this.constructDataElements(getterSetter, (A) new EmptyAnnotation());
    }

    @NotNull
    <E> Collection<DataElement<T, O, E>> constructDataElements(@NotNull ReflectiveGetterSetter<O, E> getterSetter, @NotNull A annotation);
}
