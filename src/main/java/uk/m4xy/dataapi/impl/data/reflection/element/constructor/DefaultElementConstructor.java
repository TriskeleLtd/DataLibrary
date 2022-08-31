package uk.m4xy.dataapi.impl.data.reflection.element.constructor;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetter;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.TypedFieldWrapper;
import uk.m4xy.dataapi.impl.data.reflection.ReflectiveDataType;
import uk.m4xy.dataapi.impl.data.reflection.element.ReflectiveDataElement;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.annotation.ReflectiveElementTypeDefinition;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.function.Predicate;

public class DefaultElementConstructor<T extends ReflectiveDataType<T, ?, O, ?>, O extends ReflectedDataObject<T, ?, O, ?>> implements NoAnnotationReflectiveElementConstructor<T, O>  {

    private final Predicate<ReflectiveGetterSetter<O, ?>> fieldPredicate;
    private final Function<ReflectiveGetterSetter<O, ?>, DataElement<T, O, ?>> elementConstructor;
    public DefaultElementConstructor(Predicate<ReflectiveGetterSetter<O, ?>> fieldPredicate, Function<ReflectiveGetterSetter<O, ?>, DataElement<T, O, ?>> elementConstructor) {
        this.fieldPredicate = fieldPredicate;
        this.elementConstructor = elementConstructor;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <E> Collection<DataElement<T, O, E>> constructDataElements(@NotNull ReflectiveGetterSetter<O, E> getterSetter) {
        if (fieldPredicate.test(getterSetter)) {
            return (Collection<DataElement<T, O, E>>) elementConstructor.apply(getterSetter);
        }
        return Collections.emptyList();
    }

    public static <T extends ReflectiveDataType<T, ?, O, ?>, O extends ReflectedDataObject<T, ?, O, ?>> DefaultElementConstructor<T, O> get() {
        return new DefaultElementConstructor<>(
                getterSetter -> getterSetter instanceof TypedFieldWrapper fieldWrapper
                        && Arrays.stream(fieldWrapper.getField().getAnnotations())
                        .noneMatch(a -> a.getClass().isAnnotationPresent(ReflectiveElementTypeDefinition.class)),
                ReflectiveDataElement::new);
    }
}
