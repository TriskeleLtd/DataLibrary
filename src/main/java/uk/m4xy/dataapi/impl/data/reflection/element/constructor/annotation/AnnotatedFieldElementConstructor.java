package uk.m4xy.dataapi.impl.data.reflection.element.constructor.annotation;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetter;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.TypedFieldWrapper;
import uk.m4xy.dataapi.impl.data.reflection.ReflectiveDataType;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.NoAnnotationReflectiveElementConstructor;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.ReflectiveElementConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class AnnotatedFieldElementConstructor<T extends ReflectiveDataType<T, ?, O, ?>, O extends ReflectedDataObject<T, ?, O, ?>> implements NoAnnotationReflectiveElementConstructor<T, O> {

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <E> Collection<DataElement<T, O, E>> constructDataElements(@NotNull ReflectiveGetterSetter<O, E> getterSetter) {
        if (!(getterSetter instanceof final TypedFieldWrapper<O, E> fieldWrapper)) {
            throw new IllegalArgumentException("This ElementConstructor only supports fields");
        }

        final Field field = fieldWrapper.getField();

        final Annotation[] annotations = field.getAnnotations();

            /*
            Effectively we want to be able to annotate a type with pointers eg:

            @SQLElement(column = "other_data", type = DataType.LONG, converter = OtherDataType.CONVERTER)
            @ReferenceElement(reference = OtherDataType.class, loadtime = OtherDataType.LoadTimes.AFTER)
            OtherDataType otherDataType;

            fields can have multiple elements since the elements should not record data transaction in
            reflective types since the reflective type can be modified directly — without calling an event.

             */

        List<DataElement<T, O, E>> result = new ArrayList<>();

        Arrays.stream(annotations)
                .map(a -> a.annotationType().getAnnotation(ReflectiveElementTypeDefinition.class))
                .filter(Objects::nonNull)
                .forEach(a -> {
                    try {
                        final ReflectiveElementConstructor<?, T, O> constructor = (ReflectiveElementConstructor<?, T, O>) a.value().getConstructor().newInstance();
                        result.addAll(this.getAnnotatedDataElements(getterSetter, constructor, a));
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });

        return result;

    }

    @SuppressWarnings("unchecked")
    protected <E, A extends Annotation> Collection<DataElement<T, O, E>> getAnnotatedDataElements(ReflectiveGetterSetter<O, E> getterSetter, ReflectiveElementConstructor<?, T, O> constructor, Annotation annotation) {
        return ((ReflectiveElementConstructor<A, T, O>) constructor).constructDataElements(getterSetter, (A) annotation);
    }

}
