package uk.m4xy.dataapi.impl.data.reflection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.cache.DataCache;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.reflect.ReflectedDataObject;
import uk.m4xy.dataapi.api.data.annotation.Id;
import uk.m4xy.dataapi.api.data.annotation.Key;
import uk.m4xy.dataapi.api.data.reflect.exception.AnnotationNotPresentError;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetter;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.ReflectiveGetterSetterPointer;
import uk.m4xy.dataapi.api.data.reflect.gettersetter.TypedFieldWrapper;
import uk.m4xy.dataapi.api.data.persist.leveled.LoadLevel;
import uk.m4xy.dataapi.impl.data.reflection.element.ReflectiveDataElement;
import uk.m4xy.dataapi.impl.data.reflection.element.constructor.ReflectiveElementConstructor;
import uk.m4xy.dataapi.api.data.persist.leveled.LeveledDataType;
import uk.m4xy.dataapi.impl.util.reflection.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ReflectiveDataType<
        T extends ReflectiveDataType<T, K, O, L>,
        K,
        O extends ReflectedDataObject<T, K, O, L>,
        L extends Enum<L> & LoadLevel>
        extends LeveledDataType<T, K, O, L> {
    private final Map<DataElement<T, O, ?>, ReflectiveGetterSetter<O, ?>> modifiers;
    private final Class<O> objectClass;
    private final ReflectiveGetterSetter<O, Long> idField;
    private final ReflectiveGetterSetter<O, K> keyField;

    public ReflectiveDataType(@Nullable DataCache<T, K, O> dataCache,
                              @NotNull Class<O> objectClass,
                              @NotNull ReflectiveElementConstructor<?, T, O> elementConstructor) {
        super(dataCache);

        this.objectClass = objectClass;

        this.modifiers = new ConcurrentHashMap<>();

        final List<Field> fieldsUpTo = ReflectionUtil.getFieldsUpTo(objectClass, ReflectedDataObject.class);

        Field idField = null, keyField = null;

        for (Field field : fieldsUpTo) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
            } else if (field.isAnnotationPresent(Key.class)) {
                keyField = field;
            } else {
                final TypedFieldWrapper<O, ?> gs = new TypedFieldWrapper<>(field);
                final Collection<? extends DataElement<T, O, ?>> elements = elementConstructor.constructDataElements(gs);
                for (DataElement<T, O, ?> element : elements) {
                    this.modifiers.put(element, gs);
                }
            }

        }

        if (keyField == null || idField == null) {
            throw new AnnotationNotPresentError();
        }

        this.keyField = new TypedFieldWrapper<>(keyField);
        this.idField = new TypedFieldWrapper<>(idField);

        this.registerOtherElements();
    }

    @Override
    protected void registerOtherElements() {
        // Not necessary to override
    }

    @Override
    public DataElement<T, O, Long> getIdElement() {
        return new ReflectiveDataElement<>(this.idField);
    }

    @Override
    public DataElement<T, O, K> getKeyElement() {
        return null;
    }

    public <E> void registerReflectiveDataField(@NotNull DataElement<T, O, E> dataElement, @NotNull ReflectiveGetterSetterPointer<O, E> pointer) {
        this.modifiers.put(dataElement, pointer.get());
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <E> ReflectiveGetterSetter<O, E> getModifier(@NotNull DataElement<T, O, E> element) {
        return (ReflectiveGetterSetter<O, E>) this.modifiers.get(element);
    }

    @Override
    public @NotNull K getKey(@NotNull O data) throws DataNotLoadedException {
        final K key = this.keyField.get(data);
        if (key == null) throw new DataNotLoadedException();

        return key;
    }

    @Override
    public long getId(@NotNull O data) throws DataNotLoadedException {
        final Long id = this.idField.get(data);
        if (id == null || id == -1L) throw new DataNotLoadedException();

        return id;
    }

    public @NotNull Class<O> getObjectClass() {
        return objectClass;
    }

    public void commit(@NotNull O data) {
    }

    @SafeVarargs
    public final void commit(@NotNull O data, @NotNull DataElement<T, O, ?>... dataElements) {
    }

    public void commit(@NotNull O data, @NotNull L loadLevel) {
    }

}
