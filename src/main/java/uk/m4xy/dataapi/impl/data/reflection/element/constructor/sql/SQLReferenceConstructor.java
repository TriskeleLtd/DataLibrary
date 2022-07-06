package uk.m4xy.dataapi.impl.data.reflection.element.constructor.sql;

import org.jetbrains.annotations.NotNull;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetter;
import uk.m4xy.dataapi.impl.data.reflection.ReflectiveDataType;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.AnnotationReflectiveElementConstructor;

import java.util.Collection;

public class SQLReferenceConstructor<T extends ReflectiveDataType<T, ?, O, ?>, O extends ReflectedDataObject<T, ?, O, ?>> implements AnnotationReflectiveElementConstructor<SQLElement, T, O> {


    @Override
    public @NotNull <E> Collection<DataElement<T, O, E>> constructDataElements(@NotNull ReflectiveGetterSetter<O, E> getterSetter, @NotNull SQLElement annotation) {
        return null;
    }
}
