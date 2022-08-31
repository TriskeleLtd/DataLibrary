package uk.m4xy.dataapi.impl.data.sql;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.element.exception.DataUnmodifiableException;
import uk.m4xy.dataapi.api.data.element.type.DataField;
import uk.m4xy.dataapi.api.type.TypeConverter;
import uk.m4xy.dataapi.impl.data.sql.field.PrimitiveDataField;

import java.lang.reflect.Type;
import java.sql.SQLType;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class SQLDataElement<T extends DataType<T, ?, D>, D extends Data<T, ?, D>, E> implements DataElement<T, D, E> {
    private final Type type;
    private final String column;
    private final TypeConverter<E, ?> converter;
    private final SQLType sqlType;

    private final Function<D, E> getter;
    private final BiConsumer<D, E> setter;

    public SQLDataElement(@NotNull Type type, @NotNull String column, @NotNull Function<D, E> getter, @NotNull BiConsumer<D, E> setter) {
        this(type, column, null, null, getter, setter);
    }

    public SQLDataElement(@NotNull Type type, @NotNull String column, @Nullable TypeConverter<E, ?> converter, @Nullable SQLType sqlType, @NotNull Function<D, E> getter, @NotNull BiConsumer<D, E> setter) {
        this.type = type;

        this.column = column;
        this.converter = converter == null ? TypeConverter.self(type) : converter;
        this.sqlType = sqlType == null ? SQLTypeRegistry.getInstance().inferSQLType(this.converter.getSerializedDataType()) : sqlType;

        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public @NotNull E get(@NotNull D data) throws DataNotLoadedException {
        return this.getter.apply(data);
    }

    @Override
    public @NotNull Type getType() {
        return this.type;
    }

    @NotNull
    @Override
    public DataField<E> getDataField(@NotNull D data) {
        return PrimitiveDataField.of(() -> this.getter.apply(data), v -> this.setter.accept(data, v));
    }

    @Override
    public void setDataField(@NotNull D data, @Nullable E value) throws DataUnmodifiableException {
        this.setter.accept(data, value);
    }
}
