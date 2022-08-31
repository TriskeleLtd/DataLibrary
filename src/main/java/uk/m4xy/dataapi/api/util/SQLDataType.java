package uk.m4xy.dataapi.api.util;

import java.sql.JDBCType;
import java.sql.SQLType;
import java.sql.Types;

public enum SQLDataType implements SQLType {

    VARCHAR(Types.VARCHAR),
    BIG_INT(Types.BIGINT),
    BOOLEAN(Types.BOOLEAN),

    ;

    private final int type;

    SQLDataType(int type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getVendor() {
        return null;
    }

    @Override
    public Integer getVendorTypeNumber() {
        return null;
    }

    public static SQLDataType valueOf(int type) {
        for (SQLDataType sqlType : SQLDataType.class.getEnumConstants()) {
            if (type == sqlType.type)
                return sqlType;
        }
        throw new IllegalArgumentException("Type:" + type + " is not a valid "
                + "Types.java value.");
    }

}
