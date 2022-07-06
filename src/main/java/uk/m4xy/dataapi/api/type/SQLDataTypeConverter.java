package uk.m4xy.dataapi.api.type;

import java.sql.SQLType;

public interface SQLDataTypeConverter<D, S> extends TypeConverter<D, S> {
    String getName();
    SQLType getType();
}
