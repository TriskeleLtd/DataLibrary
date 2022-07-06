package uk.m4xy.dataapi.impl.data.sql;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SQLTypeRegistry {
    private static SQLTypeRegistry INSTANCE;
    private final Map<Type, SQLType> types;

    public SQLTypeRegistry() {
        INSTANCE = this;

        this.types = new ConcurrentHashMap<>();
        this.types.put(Integer.class, JDBCType.INTEGER);
        this.types.put(Long.class, JDBCType.BIGINT);
        this.types.put(Float.class, JDBCType.DECIMAL);
        this.types.put(Short.class, JDBCType.SMALLINT);
        this.types.put(String.class, JDBCType.VARCHAR);
        this.types.put(Double.class, JDBCType.DOUBLE);
        this.types.put(Blob.class, JDBCType.BLOB);
        this.types.put(Enum.class, JDBCType.VARCHAR);
        this.types.put(Boolean.class, JDBCType.BOOLEAN);
        this.types.put(Character.class, JDBCType.CHAR);
        this.types.put(Date.class, JDBCType.DATE);
        this.types.put(Timestamp.class, JDBCType.TIMESTAMP);
    }

    public static SQLTypeRegistry getInstance() {
        return INSTANCE;
    }

    public SQLType inferSQLType(Type type) {
        return types.getOrDefault(type, JDBCType.JAVA_OBJECT);
    }


}
