package uk.m4xy.dataapi.impl.data.sql;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.m4xy.dataapi.api.data.Data;
import uk.m4xy.dataapi.api.data.DataType;
import uk.m4xy.dataapi.api.data.element.DataElement;
import uk.m4xy.dataapi.api.data.element.exception.DataNotLoadedException;
import uk.m4xy.dataapi.api.data.element.type.DataField;
import uk.m4xy.dataapi.api.type.TypeConverter;

import java.lang.reflect.Type;
import java.sql.SQLType;

public class SQLDataElement<T extends DataType<T, ?, D>, D extends Data<T, ?, D>, E> implements DataElement<T, D, E> {
    private final Type type;
    private final String column;
    private final TypeConverter<E, ?> converter;
    private final SQLType sqlType;

    public SQLDataElement(@NotNull Type type, @NotNull String column) {
        this(type, column, null, null);
    }
    public SQLDataElement(@NotNull Type type, @NotNull String column, @Nullable TypeConverter<E, ?> converter, @Nullable SQLType sqlType) {
        this.type = type;

        this.column = column;
        this.converter = converter == null ? TypeConverter.self(type) : converter;
        this.sqlType = sqlType == null ? SQLTypeRegistry.getInstance().inferSQLType(this.converter.getSerializedDataType()) : sqlType;
    }

    @Override
    public @NotNull E get(@NotNull D data) throws DataNotLoadedException {
        return null;
    }

    @Override
    public @NotNull DataField newImplementation(@Nullable E value) {
        return null;
    }

    @Override
    public @NotNull Type getType() {
        return this.type;
    }
}
