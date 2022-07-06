package uk.m4xy.dataapi.impl.data.reflection.element.constructor;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetter;
import uk.m4xy.dataapi.impl.data.reflection.ReflectiveDataType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CombinedElementConstructor<
        T extends ReflectiveDataType<T, ?, O, ?>,
        O extends ReflectedDataObject<T, ?, O, ?>>
        implements NoAnnotationReflectiveElementConstructor<T, O> {

    private final ReflectiveElementConstructor<?, T, O>[] elementConstructors;

    @SafeVarargs
    public CombinedElementConstructor(ReflectiveElementConstructor<?, T, O>... elementConstructors) {
        this.elementConstructors = elementConstructors;
    }

    @Override
    public @NotNull <E> Collection<DataElement<T, O, E>> constructDataElements(@NotNull ReflectiveGetterSetter<O, E> getterSetter) {
        List<DataElement<T, O, E>> result = new ArrayList<>();
        for (ReflectiveElementConstructor<?, T, O> elementConstructor : elementConstructors) {
            result.addAll(elementConstructor.constructDataElements(getterSetter));
        }

        return result;
    }
}
