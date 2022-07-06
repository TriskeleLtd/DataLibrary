package uk.m4xy.dataapi.impl.data.reflection;

public class $ReflectiveDataStore {} /*<
        T extends ReflectiveDataType<T, K, O, L>,
        K,
        O extends ReflectedDataObject<T, K, O, L>,
        L extends Enum<L> & LoadLevel> implements Data<T, K> {
    private final ReflectiveGetterSetter<O, Long> idField;
    private final ReflectiveGetterSetter<O, K> keyField;

    private final Map<DataElement<T, ?>, ReflectiveGetterSetterPointer<O, ?>> pointers;
    private final O dataObject;
    private final Map<DataElement<T, ?>, ReflectiveDataField<T, K, O, L, ?>> dataFieldMap;

    public $ReflectiveDataStore(DataType<?, K> dataType, O reflectedDataObject, Map<DataElement<T, ?>, ReflectiveGetterSetterPointer<O, ?>> fieldPointers) {

        this.pointers = new ConcurrentHashMap<>(fieldPointers);

        this.dataObject = reflectedDataObject;

        final List<Field> fieldsUpTo = ReflectionUtil.getFieldsUpTo(reflectedDataObject.getClass(), Object.class);

        Field idField = null, keyField = null;

        this.dataFieldMap = new ConcurrentHashMap<>();

        for (Field field : fieldsUpTo) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
                continue;
            }

            if (field.isAnnotationPresent(Key.class)) {
                keyField = field;
                continue;
            }

            // TODO handle other fields
        }

        if (keyField == null || idField == null) {
            throw new AnnotationNotPresentError();
        }

        this.keyField = new TypedFieldWrapper<>(keyField);
        this.idField = new TypedFieldWrapper<>(idField);

    }

    @Override
    public @NotNull K getKey() {
        final K key = this.keyField.get(dataObject);

        if (key == null) {
            throw new DataNotLoadedException();
        }

        return key;
    }

    @Override
    public long getId() {
        final Long id = this.idField.get(dataObject);

        if (id == null) {
            throw new DataNotLoadedException();
        }

        return id;
    }

    @Override
    public @Nullable <E> DataField getDataField(@NotNull DataElement<T, E> dataElement) {
        return this.dataFieldMap.get(dataElement);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> void setDataField(@NotNull DataElement<T, E> dataElement, @Nullable E value) {
        final DataField dataField = this.getDataField(dataElement);

        if (dataField == null) {
            throw ReflectObjectMissingError.from((Class<O>) this.dataObject.getClass(), (ReflectiveGetterSetterPointer<O, ?>) pointers.get(dataElement));
        } else {
            if (!dataField.isSettable()) throw new DataUnmodifiableException(dataElement);

            final ModifiableDataField<E> modifiableDataField = (ModifiableDataField<E>) dataField;
            modifiableDataField.set(value);
        }
    }

    public O getDataObject() {
        return dataObject;
    }
}
*/
